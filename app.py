from flask import Flask, request, jsonify
from openai import OpenAI
from dotenv import load_dotenv
import os
import json
import re
import math
from typing import Optional

# =====================[ 기본 설정 ]=====================
load_dotenv()
api_key = os.getenv("OPENAI_API_KEY")

app = Flask(__name__)
client = OpenAI(api_key=api_key)  # 환경 변수에서 불러온 키 사용

# Flask 바깥 모듈에서 프롬프트 가져오기
from prompt_template import base_prompt  # noqa: E402

# =====================[ 하드웨어/보정 파라미터 ]=====================

# 액상 펌프 최소 구동시간(스톨/유휴구간 방지)
MIN_ON_TIME = 0.10  # sec
# 타이머 해상도(컨트롤러 타이머 tick에 맞춰 반올림). 50ms 예시.
TIME_STEP = 0.05

def _round_to_step(value: float, step: float = TIME_STEP) -> float:
    return round(value / step) * step

# 실험치 선형 근사 (g = a*t + b) → t = (g - b)/a
def soy_time_sec(target_g: float) -> float:
    # 간장
    t = (float(target_g) - 0.3893110109465644) / 42.6741790083709
    t = max(MIN_ON_TIME, t)
    return _round_to_step(t)

def allulose_time_sec(target_g: float) -> float:
    # 알룰로스(액상 감미료)
    t = (float(target_g) - 0.4144236960721128) / 47.222794591113974
    t = max(MIN_ON_TIME, t)
    return _round_to_step(t)

# 가루 1펌프 당 g (실험 결과)
POWDER_GRAMS_PER_PUMP = {
    "다시다": 4.4,
    "고춧가루": 2.2,
    "고추장 분말": 3.0,
}

def powder_pumps(ingredient_name: str, target_g: float):
    gpp = POWDER_GRAMS_PER_PUMP.get(ingredient_name)
    if not gpp:
        # 정의 없으면 수동 처리 유도
        return 0, 0.0, 0.0
    pumps = math.ceil(float(target_g) / gpp)
    delivered_g = pumps * gpp
    overshoot_g = delivered_g - float(target_g)
    return pumps, delivered_g, overshoot_g

# =====================[ 이름 표준화/대체 규칙 (최소화) ]=====================

def normalize_ingredient(name: str) -> str:
    n = (name or "").strip()
    # 설탕은 액상 알룰로스로 대체(하드웨어 구성 반영)
    if n in ["설탕", "백설탕", "자당", "슈가", "Sugar", "sugar"]:
        return "알룰로스"
    # 고추장은 분말로 대체(안전망: 프롬프트에서 이미 강제함)
    if n in ["고추장", "Gochujang", "gochujang"]:
        return "고추장 분말"
    return n

# =====================[ 단위 파싱 ]=====================

AMOUNT_RE = re.compile(r"^\s*(\d+\.?\d*)\s*([a-zA-Z가-힣]{1,5})?\s*$")

def parse_amount_to_grams(amount_str: str) -> Optional[float]:
    """
    기본 단위 g 가정. 액체 'ml'은 밀도≈1로 g로 간주.
    """
    if amount_str is None:
        return None
    m = AMOUNT_RE.match(str(amount_str))
    if not m:
        return None
    value = float(m.group(1))
    unit = (m.group(2) or "g").lower()

    if unit in ["g", "그램"]:
        return value
    if unit in ["ml", "밀리리터"]:
        return value  # 밀도≈1 가정
    # 숫자만 온 경우도 g로 처리
    return value

# =====================[ 펌프 하드웨어 매핑 ]=====================

PUMP_MAP = {
    "간장": {"pump_id": "soy_sauce_pump_1", "type": "liquid"},
    "알룰로스": {"pump_id": "allulose_pump_1", "type": "liquid"},
    "다시다": {"pump_id": "powder_pump_dashida", "type": "powder"},
    "고춧가루": {"pump_id": "powder_pump_chili", "type": "powder"},
    "고추장 분말": {"pump_id": "powder_pump_gochujang", "type": "powder"},
}
AVAILABLE_INGREDIENTS = set(PUMP_MAP.keys())

# =====================[ 레시피 → 디스펜서 변환 ]=====================

def transform_recipe_for_dispenser(ai_items: list) -> dict:
    """
    ai_items 예시:
    [
      {"ingredient": "간장", "amount": "20g"},
      {"ingredient": "설탕", "amount": "12g"},   # -> "알룰로스"로 치환
      {"ingredient": "고춧가루", "amount": "5g"},
      {"ingredient": "된장", "amount": "2g", "manual": true}
    ]
    """
    auto_ingredients = []
    manual_ingredients = []

    for item in ai_items:
        raw_name = item.get("ingredient") or item.get("name")
        ing = normalize_ingredient(raw_name)

        # 수동 표시가 명시돼 있으면 무조건 수동 바구니로
        if item.get("manual", False):
            manual_ingredients.append({
                "ingredient": ing,
                "type": "manual",
                "amount": item.get("amount"),
                "manual": True
            })
            continue

        # g 추출(ml도 g로 환산)
        target_g = parse_amount_to_grams(item.get("amount"))

        if ing in AVAILABLE_INGREDIENTS and target_g is not None:
            hw = PUMP_MAP[ing]
            if hw["type"] == "liquid":
                # 액상은 시간 계산
                if ing == "간장":
                    t = soy_time_sec(target_g)
                elif ing == "알룰로스":
                    t = allulose_time_sec(target_g)
                else:
                    t = MIN_ON_TIME

                auto_ingredients.append({
                    "ingredient": ing,
                    "type": "liquid",
                    "target_g": target_g,
                    "pump_id": hw["pump_id"],
                    "pump_time_sec": t
                })
            else:
                # 가루는 펌프 수
                pumps, delivered_g, overshoot_g = powder_pumps(ing, target_g)
                auto_ingredients.append({
                    "ingredient": ing,
                    "type": "powder",
                    "target_g": target_g,
                    "pump_id": hw["pump_id"],
                    "pump_counts": int(pumps),
                    "per_pump_g": POWDER_GRAMS_PER_PUMP.get(ing, 0.0),
                    "estimated_delivered_g": delivered_g,
                    "overshoot_g": overshoot_g
                })
        else:
            # 자동 불가/양 미확정 → 수동
            manual_ingredients.append({
                "ingredient": ing,
                "type": "manual",
                "amount": item.get("amount"),
                "manual": True
            })

    return {
        "auto_ingredients": auto_ingredients,
        "manual_ingredients": manual_ingredients
    }

# =====================[ GPT 응답 JSON 추출 보강 ]=====================

JSON_ARRAY_RE = re.compile(r"\[\s*{.*}\s*\]", re.DOTALL)

def extract_json_array(text: str):
    """
    GPT가 코드블록이나 앞뒤 텍스트를 붙여 보낼 때를 대비해
    첫 번째 JSON 배열만 안전하게 추출.
    """
    if not text:
        raise ValueError("Empty response from model")
    # 코드블록 제거
    cleaned = text.strip()
    if cleaned.startswith("```"):
        cleaned = re.sub(r"^```(?:json)?\s*|\s*```$", "", cleaned, flags=re.DOTALL)
    # 배열 패턴 추출
    m = JSON_ARRAY_RE.search(cleaned)
    src = m.group(0) if m else cleaned
    return json.loads(src)

# =====================[ 요청 문장에서 이름 자동 추출 ]=====================

# 끝의 명령/정중 표현 제거 패턴 (…만들어줘/알려줘/해주세요/줘 등)
REQUEST_VERB_TAIL_RE = re.compile(
    r'(를|을)?\s*(레시피\s*)?(좀\s*)?'
    r'(만들어\s*줘|알려\s*줘|해\s*줘|추천해\s*줘|해주세요|주세요|줘)\s*$'
)

def infer_name_from_request(text: str) -> str:
    """
    예)
      - '궁중떡볶이 양념 만들어줘'  -> '궁중떡볶이 양념'
      - '엽떡 소스 레시피 알려줘'    -> '엽떡 소스 레시피'
      - '신전 떡볶이 만들어줘'       -> '신전 떡볶이 레시피'
    """
    if not text:
        return ""
    t = text.strip()
    # 끝의 명령/정중 표현 제거
    t = REQUEST_VERB_TAIL_RE.sub('', t).strip(' "')
    # 문장 내에 이미 '양념/소스/레시피'가 들어있으면 그대로 사용
    if re.search(r'(양념|소스|레시피)', t):
        return t
    # 없으면 기본적으로 '레시피'를 붙여 이름화
    return f"{t} 레시피"

# =====================[ 엔드포인트 ]=====================

# 메인 엔드포인트(+호환 별칭)
@app.route('/get-recipe', methods=['POST'])
@app.route('/api/recipe', methods=['POST'])
def get_recipe():
    data = request.get_json(silent=True) or {}

    # ✅ 사용자 입력(필수)
    user_input = (data.get("request") or "").strip()
    if not user_input:
        return jsonify({"error": "Missing 'request' in JSON body"}), 400

    # ✅ 레시피 이름: 명시적으로 받으면 우선, 없으면 자동 추출 → 기본값
    recipe_name = (data.get("recipe_name") or "").strip()
    if not recipe_name:
        recipe_name = infer_name_from_request(user_input)
    if not recipe_name:
        recipe_name = "사용자 지정 레시피"

    # ✅ 프롬프트 보강: 표준명과 단위 강제(이름/단위 표준화를 모델에 맡김)
    prompt_hint = """
규칙:
- 모든 재료 이름은 반드시 아래 표준명만 사용하세요: 간장, 알룰로스, 고춧가루, 다시다, 고추장 분말
- 위 표준명 외의 변형은 절대 사용하지 마세요. (예: 고추장가루, Gochujang powder → 고추장 분말)
- 모든 양은 반드시 '숫자g' 형식으로만 표기하세요. 예) 12g
- 출력은 JSON 배열만 반환하세요. 각 원소는 {"ingredient": "<표준명>", "amount": "<숫자g>"} 형식입니다.
"""
    # 모델에게는 재료/양만 요청 (이름은 서버에서 붙임)
    full_prompt = f"{base_prompt}\n\n요청: {user_input}\n{prompt_hint}"

    try:
        # 모델은 팀/계정 환경에 맞게 조정(gpt-4, gpt-4.1 등)
        response = client.chat.completions.create(
            model="gpt-4",
            messages=[{"role": "user", "content": full_prompt}],
        )
        content = response.choices[0].message.content
        ai_recipe_items = extract_json_array(content)
        if not isinstance(ai_recipe_items, list):
            raise ValueError("Model did not return a JSON array.")
    except Exception as e:
        return jsonify({"error": f"Model parsing error: {str(e)}"}), 500

    # 디스펜서 제어용 변환
    final_payload = transform_recipe_for_dispenser(ai_recipe_items)

    # ✅ 최종 응답에 name 포함 (명시값 > 자동추출 > 기본값)
    result = {
        "name": recipe_name,
        **final_payload
    }
    return jsonify(result), 200

@app.route("/", methods=["GET"])
def home():
    return "✅ Flask 서버 실행 중입니다!"

# =====================[ 실행 ]=====================
if __name__ == '__main__':
    # 외부에서 접근해야 하면 host='0.0.0.0' 지정
    app.run(debug=True)

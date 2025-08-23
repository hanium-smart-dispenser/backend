package com.hanium.smartdispenser.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.smartdispenser.ai.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AiResponse getRecipe(String prompt) {
        String url = "http://host.docker.internal:5000/get-recipe";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(Map.of("request", prompt), headers);

        ResponseEntity<String> res = restTemplate.postForEntity(url, entity, String.class);
        String body = res.getBody();

        log.info("FLASK RAW = {}", body);
        try {
            AiResponse dto = objectMapper.readValue(body, AiResponse.class);
            if (dto.name() == null || dto.name().isBlank()) {
                throw new IllegalStateException("이름이 누락 되었습니다.");
            }
            return dto;
        } catch (Exception e) {
            throw new IllegalStateException("AI JSON 파싱 실패");
        }
    }
}

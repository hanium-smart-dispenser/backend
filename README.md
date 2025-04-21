# Smart Dispenser Backend

> 자동 소스 조합 디스펜서를 제어하는 Spring Boot 기반 백엔드 시스템

![status](https://img.shields.io/badge/status-developing-yellow)

## 📌 프로젝트 개요

- 사용자의 요청을 OpenAI API로 분석해 레시피 생성
- MQTT를 통해 라즈베리파이 기반 디스펜서 제어
- 앱과 REST API로 통신하며 사용자, 레시피, 이력 등을 관리
- MySQL 기반 데이터베이스 사용

---

## 🛠️ 기술 스택

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.4
- **Database**: MySQL
- **Communication**: REST API, MQTT
- **Other**: OpenAI API, JUnit5

---

## 📁 디렉토리 구조

```shell
src/main/java/com/hanium/smartdispenser
├── user              # 사용자 도메인
├── dispenser         # 디스펜서 관련 로직
├── recipe            # 레시피 및 재료 조합
├── history           # 요청 이력 저장
├── common            # 예외, 설정, 유틸
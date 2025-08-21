package com.hanium.smartdispenser.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AiService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public JsonNode getRecipeRaw(String prompt) {
        String url = "http://host.docker.internal:5000/test";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(Map.of("request", prompt), headers);

        ResponseEntity<String> res = restTemplate.postForEntity(url, entity, String.class);
        String body = res.getBody();
        try {
            return objectMapper.readTree(body);
        } catch (Exception e) {
            throw new IllegalStateException("AI JSON 파싱 실패");
        }
    }
}

package com.hanium.smartdispenser.common.exception;

import com.hanium.smartdispenser.user.exception.DuplicateEmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {

        List<Map<String,String>> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                )).toList();

        return ResponseEntity.badRequest().body(Map.of("status", 400, "errors", errors));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(Map.of("status", 400, "message", e.getMessage()));
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<?> handleInfraException(InfrastructureException e) {
        return ResponseEntity.badRequest().body(Map.of("status", 500, "message", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e) {
        log.error("[서버 예외]",e);

        return ResponseEntity.internalServerError().body(Map.of("status", 500, "message", "서버 내부 오류가 발생했습니다."));
    }
}

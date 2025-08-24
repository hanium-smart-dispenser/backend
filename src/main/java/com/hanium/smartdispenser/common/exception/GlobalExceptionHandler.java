package com.hanium.smartdispenser.common.exception;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<Map<String,String>> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                )).toList();

        log.warn("ValidationException uri={} method={} errors={}",
                request.getRequestURI(),
                request.getMethod(),
                errors
        );

        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "code","VALIDATION_ERROR",
                "message","요청 값이 올바르지 않습니다.",
                "errors", errors
        ));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("BusinessException uri={} method={} code={} ctx={}",
                request.getRequestURI(),
                request.getMethod(),
                e.getErrorCode(),
                e.getContext(),
                e);
        return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "code", e.getErrorCode(),
                "message", e.getMessage()
        ));
    }

    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<?> handleInfraException(InfrastructureException e, HttpServletRequest request) {
        log.error("InfrastructureException uri={} method={} code={} ctx={}",
                request.getRequestURI(),
                request.getMethod(),
                e.getErrorCode(),
                e.getContext(),
                e);

        return ResponseEntity.internalServerError().body(Map.of(
                "status", 500,
                "code", e.getErrorCode(),
                "message", e.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e, HttpServletRequest request) {
        log.error("Unhandled Exception uri={} method={} exClass={} message={}",
                request.getRequestURI(),
                request.getMethod(),
                e.getClass().getSimpleName(),
                e.getMessage(), e);

        return ResponseEntity.internalServerError().body(Map.of(
                "status", 500,
                "code", "INTERNAL_SERVER_ERROR",
                "message", "서버 내부 오류가 발생했습니다."
        ));
    }
}

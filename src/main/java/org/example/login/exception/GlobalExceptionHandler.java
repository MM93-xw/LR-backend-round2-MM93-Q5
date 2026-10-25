package org.example.login.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException e) {
        System.out.println("====== 参数异常 BadRequestException ======");
        return ResponseEntity.badRequest().body(Map.of("code", 400, "message", e.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException e) {
        System.out.println("====== 业务异常 BusinessException ======");
        return ResponseEntity.badRequest().body(Map.of("code", 400, "message", e.getMessage()));
    }
}
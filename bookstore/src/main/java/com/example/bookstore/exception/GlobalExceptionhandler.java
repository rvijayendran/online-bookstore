package com.example.bookstore.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionhandler { 
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String , Object >> handleNotFound(ResourceNotFoundException ex){
        return build(HttpStatus.NOT_FOUND , ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String , Object>> handleDuplicate(DuplicateResourceException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());

    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String , Object>> handleStock(InsufficientStockException ex)
    {
        return build(HttpStatus.CONFLICT , ex.getMessage());
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<Map<String , Object>> handleInvalidOp(InvalidOperationException ex){
        return build(HttpStatus.BAD_REQUEST , ex.getMessage());
    }
    
    private ResponseEntity<Map<String,Object>> build(HttpStatus status , String message)
    {
        Map<String , Object > body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}

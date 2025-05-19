package com.merchant.merchant_service.exception;

import com.merchant.merchant_service.exception.custom.BadRequestException;
import com.merchant.merchant_service.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Handle validation errors from @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.put(err.getField(), err.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                fieldErrors,
                request.getRequestURI()
        ));
    }

    // ✅ Handle custom BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                null,
                request.getRequestURI()
        ));
    }

    // ✅ Handle any other uncaught exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, HttpServletRequest request) {
        ex.printStackTrace(); // Log it
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                null,
                request.getRequestURI()
        ));
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                null,
                request.getRequestURI()
        ));
    }


    private Map<String, Object> buildErrorResponse(HttpStatus status, String message, Object errors, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        if (errors != null) body.put("errors", errors);
        return body;
    }
}

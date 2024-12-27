package org.team14.newsfeed.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * CustomException 예외 처리
     *
     * @param e CustomException
     * @return ErrorResponse
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        Map<String, Object> response = ErrorResponse.ofCustomException(
                e);
        log.info("[{}] {} : {}", e.getStackTrace()[0], e.getHttpStatus(), e.getErrorMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    /**
     * RequestBody Validation 예외 처리
     *
     * @param e MethodArgumentNotValidException
     * @return ErrorResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> response = ErrorResponse.ofMethodArgumentNotValidException(e);
        log.info("[{}] {} : {}", e.getStackTrace()[0], HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * RequestParam Validation 예외 처리
     *
     * @param e ConstraintViolationException
     * @return ErrorResponse
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, Object> response = ErrorResponse.ofConstraintViolationException(e);
        log.info("[{}] {} : {}", e.getStackTrace()[0], HttpStatus.BAD_REQUEST, e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Exception 예외 처리
     *
     * @param e Exception
     * @return ErrorResponse
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception e) {
        Map<String, Object> response = ErrorResponse.ofException(
                HttpStatus.INTERNAL_SERVER_ERROR, e);
        log.error("[{}] {} : {}", e.getStackTrace()[0], HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

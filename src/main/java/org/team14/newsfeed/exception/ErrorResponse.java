package org.team14.newsfeed.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exception에 대한 ErrorResponse 생성
 */
@Getter
public class ErrorResponse {

    // 에러 코드
    private final int code;

    // 에러 타입
    private final HttpStatus httpStatus;

    // 에러 메시지
    private final String message;

    private ErrorResponse(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static Map<String, Object> ofCustomException(CustomException e) {
        return createErrorResponse(e.getHttpStatus(), e.getErrorMessage());
    }

    public static Map<String, Object> ofMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));

        return createErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    public static Map<String, Object> ofConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("\n"));

        return createErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    public static Map<String, Object> ofException(HttpStatus httpStatus, Exception e) {
        return createErrorResponse(httpStatus, e.getMessage());
    }

    private static Map<String, Object> createErrorResponse(HttpStatus httpStatus, String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", httpStatus.value());
        response.put("httpStatus", httpStatus);
        response.put("message", errorMessage);

        return response;
    }
}

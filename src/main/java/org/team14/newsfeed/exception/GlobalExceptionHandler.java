package org.team14.newsfeed.exception;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 전역 예외 처리
   *
   * @param e Exception
   * @return errorResponse
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGlobalException(Exception e) {
    // CustomException인 경우
    if (e instanceof BaseException baseException) {
      Map<String, Object> response = ErrorResponse.responseFromCustomException(baseException);
      log.error("[{}] {} : {}", baseException.getClass().getSimpleName(),
          baseException.getHttpStatus(),
          baseException.getErrorMessage());

      return ResponseEntity.status(baseException.getHttpStatus()).body(response);
    } else { // CustomException이 아닌 경우
      Map<String, Object> response = ErrorResponse.responseFromException(
          HttpStatus.INTERNAL_SERVER_ERROR);
      log.error("[{}] {} : {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR,
          e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}

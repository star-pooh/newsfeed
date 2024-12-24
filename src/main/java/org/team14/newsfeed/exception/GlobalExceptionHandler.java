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
    if (e instanceof CustomBaseException customBaseException) {
      Map<String, Object> response = ErrorResponse.createErrorResponseFromCustomException(
          customBaseException);
      log.error("[{}.{}] {} : {}", customBaseException.getClassName(),
          customBaseException.getClass().getSimpleName(),
          customBaseException.getHttpStatus(),
          customBaseException.getErrorMessage());

      return ResponseEntity.status(customBaseException.getHttpStatus()).body(response);
    } else { // CustomException이 아닌 경우
      Map<String, Object> response = ErrorResponse.createErrorResponseFromException(
          HttpStatus.INTERNAL_SERVER_ERROR);
      log.error("[{}] {} : {}", e.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR,
          e.getMessage());

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}

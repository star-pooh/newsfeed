package org.team14.newsfeed.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Validation에서 발생한 예외의 정보 설정
 */
@Getter
public class CustomValidationException extends RuntimeException implements BaseException {

  private final HttpStatus httpStatus;
  private final String errorMessage;

  public CustomValidationException(HttpStatus httpStatus, String errorMessage) {
    super(errorMessage);
    this.httpStatus = httpStatus;
    this.errorMessage = errorMessage;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return this.httpStatus;
  }

  @Override
  public String getErrorMessage() {
    return this.errorMessage;
  }
}

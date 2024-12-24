package org.team14.newsfeed.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * CustomException을 위한 부모 클래스
 * <p>
 * httpStatus와 errorMessage를 설정
 */
@Getter
public class CustomBaseException extends RuntimeException {

  private final String className;
  private final HttpStatus httpStatus;
  private final String errorMessage;

  public CustomBaseException(String className, HttpStatus httpStatus, String errorMessage) {
    this.className = className;
    this.httpStatus = httpStatus;
    this.errorMessage = errorMessage;
  }
}

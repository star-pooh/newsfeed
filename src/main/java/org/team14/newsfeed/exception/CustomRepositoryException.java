package org.team14.newsfeed.exception;

import org.springframework.http.HttpStatus;

/**
 * Respository에서 발생한 예외의 정보 설정
 */
public class CustomRepositoryException extends CustomBaseException {

  public CustomRepositoryException(String className, HttpStatus httpStatus,
      String errorMessage) {
    super(className, httpStatus, errorMessage);
  }
}

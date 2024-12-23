package org.team14.newsfeed.exception;

import org.springframework.http.HttpStatus;

/**
 * CustomException 인터페이스
 * <p>
 * httpStatus와 errorMessage를 설정
 */
public interface BaseException {

  HttpStatus getHttpStatus();

  String getErrorMessage();
}

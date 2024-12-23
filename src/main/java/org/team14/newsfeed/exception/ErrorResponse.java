package org.team14.newsfeed.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.http.HttpStatus;

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

  /**
   * CustomException에 대한 ErrorResponse 생성
   *
   * @param e   CustomException
   * @param <T> CustomException
   * @return response
   */
  public static <T extends BaseException> Map<String, Object> responseFromCustomException(T e) {
    Map<String, Object> response = new HashMap<>();
    response.put("code", e.getHttpStatus().value());
    response.put("httpStatus", e.getHttpStatus());
    response.put("message", e.getErrorMessage());

    return response;
  }

  /**
   * CustomException 이외의 Exception에 대한 ErrorResponse 생성
   *
   * @param httpStatus HttpStatus
   * @return response
   */
  public static Map<String, Object> responseFromException(HttpStatus httpStatus) {
    Map<String, Object> response = new HashMap<>();
    response.put("code", httpStatus.value());
    response.put("httpStatus", httpStatus);
    response.put("message", "예기치 못한 에러가 발생했습니다.");

    return response;
  }
}

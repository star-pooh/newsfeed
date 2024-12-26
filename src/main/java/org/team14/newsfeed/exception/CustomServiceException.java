package org.team14.newsfeed.exception;

import org.springframework.http.HttpStatus;

/**
 * Service 에서 발생한 예외의 정보 설정
 */
public class CustomServiceException extends CustomBaseException {

    public CustomServiceException(String className, HttpStatus httpStatus,
                                  String errorMessage) {
        super(className, httpStatus, errorMessage);
    }

    /**
     * 사용자 리소스를 찾을 수 없을 때 발생하는 예외
     */
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * 비밀번호가 일치하지 않는 경우 발생하는 예외
     */
    public static class PasswordMismatchException extends RuntimeException {
        public PasswordMismatchException(String message) {
            super(message);
        }
    }
}

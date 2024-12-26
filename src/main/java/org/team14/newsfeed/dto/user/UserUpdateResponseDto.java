package org.team14.newsfeed.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.team14.newsfeed.entity.User;

/**
 * 사용자 업데이트 요청에 대한 응답 DTO입니다.
 * 클라이언트로 반환할 사용자 정보를 포함합니다.
 */
@Getter
@AllArgsConstructor
public class UserUpdateResponseDto {

    private Long id;
    private String username;
    private String email;

    /**
     * User 엔티티 객체를 기반으로 UserUpdateResponseDto 생성
     *
     * @param user 업데이트된 User 엔티티 객체
     * @return UserUpdateResponseDto
     */
    public static UserUpdateResponseDto of(User user) {
        return new UserUpdateResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
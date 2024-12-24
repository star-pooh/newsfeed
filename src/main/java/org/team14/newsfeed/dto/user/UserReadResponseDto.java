package org.team14.newsfeed.dto.user;

import lombok.Getter;
import org.team14.newsfeed.entity.User;

@Getter
public class UserReadResponseDto {

    // 사용자 이름
    private final String username;

    // 이메일
    private final String email;

    private UserReadResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static UserReadResponseDto of(User user) {
        return new UserReadResponseDto(user.getUsername(), user.getEmail());
    }
}

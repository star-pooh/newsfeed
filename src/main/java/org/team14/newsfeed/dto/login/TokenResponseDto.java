package org.team14.newsfeed.dto.login;

import lombok.Getter;

@Getter
public class TokenResponseDto {

    // JWT 토큰
    private final String token;

    private TokenResponseDto(String token) {
        this.token = token;
    }

    public static TokenResponseDto of(String token) {
        return new TokenResponseDto(token);
    }
}

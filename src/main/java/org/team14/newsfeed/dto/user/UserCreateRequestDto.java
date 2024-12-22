package org.team14.newsfeed.dto.user;

import lombok.Getter;

@Getter
public class UserCreateRequestDto {

  // 사용자 이름
  private final String username;

  // 이메일
  private final String email;

  // 비밀번호
  private final String password;

  private UserCreateRequestDto(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}

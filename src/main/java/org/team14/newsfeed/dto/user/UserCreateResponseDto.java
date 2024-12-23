package org.team14.newsfeed.dto.user;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.team14.newsfeed.entity.User;

@Getter
public class UserCreateResponseDto {

  // 사용자 이름
  private final String username;

  // 이메일
  private final String email;

  // 생성일
  private final String createdAt;

  // 수정일
  private final String updatedAt;

  private UserCreateResponseDto(String username, String email, String createdAt, String updatedAt) {
    this.username = username;
    this.email = email;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  /**
   * UserCreateResponseDto로 변환
   *
   * @param user 사용자 정보
   * @return UserCreateResponseDto
   */
  public static UserCreateResponseDto of(User user) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    return new UserCreateResponseDto(
        user.getUsername(),
        user.getEmail(),
        user.getCreatedAt().format(dtf),
        user.getUpdatedAt().format(dtf)
    );
  }
}

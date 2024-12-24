package org.team14.newsfeed.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    // 사용자 이름
    private String username;

    // 이메일
    private String email;
}
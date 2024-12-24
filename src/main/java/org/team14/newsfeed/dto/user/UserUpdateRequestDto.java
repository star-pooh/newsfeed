package org.team14.newsfeed.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    // 사용자 이름
    @NotNull(message = "Username cannot be null")
    private String username;

    // 이메일
    @Email(message = "Invalid email format")
    private String email;
}
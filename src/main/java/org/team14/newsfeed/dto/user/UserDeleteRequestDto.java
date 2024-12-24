package org.team14.newsfeed.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {

    // 이메일
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private final String email;

    // 비밀번호
    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=\\S+$).{8,}$",
            message = "비밀번호는 최소 8자리 이상이어야 하며, 숫자, 문자, 특수문자를 포함해야 합니다.")
    private final String password;

    // 생성자
    public UserDeleteRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

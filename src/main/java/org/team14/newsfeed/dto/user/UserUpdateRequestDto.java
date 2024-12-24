package org.team14.newsfeed.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    // 사용자 이름
    @NotNull(message = "사용자 이름은 필수 입력값입니다.")
    private String username;

    // 이메일
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    // 현재 비밀번호
    @NotNull(message = "현재 비밀번호를 입력하세요.")
    private String currentPassword;

    /**
     * ^: 문자열의 시작.
     * (?=.*[a-zA-Z]): 대문자 또는 소문자가 최소 한 글자 포함되어야 함
     * (?=.*\d): 숫자가 최소 한 글자 포함되어야 함
     * (?=.*[\W_]): 특수문자가 최소 한 글자 포함되어야 함 (\W는 알파벳, 숫자가 아닌 문자)
     * $: 문자열의 끝
     */
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_])$",
            message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다."
    )
    @Size(min = 8, message = "새 비밀번호는 최소 8글자 이상이어야 합니다.")
    private String newPassword;
}
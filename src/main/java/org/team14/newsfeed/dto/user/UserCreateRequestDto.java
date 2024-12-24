package org.team14.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {

    // 사용자 이름
    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
    private final String username;

    /**
     * ^: 문자열의 시작.
     * <p>
     * [a-zA-Z0-9._]+: 이메일의 로컬 부분은 영문자, 숫자, _를 포함할 수 있음. 최소 한 글자 이상이어야 함.
     * <p>
     * \@: 반드시 포함되어야 하는 @ 기호.
     * <p>
     * [a-zA-Z0-9.-]+: 도메인 이름은 영문자, 숫자, . 또는 -를 포함할 수 있음. 최소 한 글자 이상이어야 함.
     * <p>
     * \.[a-zA-Z]{2,}: 도메인 이름의 마지막 부분은 . 다음에 영문자 2자 이상이어야 함 (예: .com, .org, .io).
     * <p>
     * $: 문자열의 끝.
     */
    // 이메일
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private final String email;

    /**
     * ^: 문자열의 시작.
     * <p>
     * (?=.*[a-zA-Z]): 대문자 또는 소문자가 최소 한 글자 포함되어야 함.
     * <p>
     * (?=.*\d): 숫자가 최소 한 글자 포함되어야 함.
     * <p>
     * (?=.*[\W_]): 특수문자가 최소 한 글자 포함되어야 함 (\W는 알파벳, 숫자가 아닌 문자).
     * <p>
     * $: 문자열의 끝.
     */
    // 비밀번호
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).*$",
            message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;

    private UserCreateRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

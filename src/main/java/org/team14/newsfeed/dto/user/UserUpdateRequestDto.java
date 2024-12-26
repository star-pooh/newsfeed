package org.team14.newsfeed.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * 사용자 업데이트 요청을 위한 DTO
 * 사용자 정보(이름, 이메일, 비밀번호 등)를 포함하며,
 * 불변 객체로 생성하기 위해 @AllArgsConstructor 사용
 */
@Getter
@AllArgsConstructor // 생성자를 자동으로 생성하여 불변 객체로 만듬
public class UserUpdateRequestDto {
    // 사용자 이름
    @NotBlank(message = "사용자 이름은 필수 입력값입니다.")
    private String username;

    // 사용자 이메일
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    // 사용자 현재 비밀번호
    @NotBlank(message = "현재 비밀번호는 필수 입력값입니다.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "현재 비밀번호는 최소 8자 이상이며, 영문/숫자/특수문자를 각각 하나 이상 포함해야 합니다."
    )
    private String currentPassword;

    // 사용자 새 비밀번호
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_])$",
            message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야합니다."
    )
    @Size(min = 8, message = "새 비밀번호는 최소 8글자 이상이어야 합니다.")
    private String newPassword;
}

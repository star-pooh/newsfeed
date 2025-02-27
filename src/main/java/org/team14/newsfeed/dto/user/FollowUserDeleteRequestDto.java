package org.team14.newsfeed.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowUserDeleteRequestDto {

    @NotBlank(message = "Email은 필수입니다.")
    private final String followedUserEmail;
}

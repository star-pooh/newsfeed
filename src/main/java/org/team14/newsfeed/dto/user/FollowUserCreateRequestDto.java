package org.team14.newsfeed.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowUserCreateRequestDto {

    private final String followingUserEmail;

    private final String followedUserEmail;

}

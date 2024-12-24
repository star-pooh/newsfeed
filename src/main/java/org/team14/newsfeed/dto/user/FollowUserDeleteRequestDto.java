package org.team14.newsfeed.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowUserDeleteRequestDto {

    private final String followingUserEmail;

    private final String followedUserEmail;


}

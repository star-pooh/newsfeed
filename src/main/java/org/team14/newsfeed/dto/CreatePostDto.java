package org.team14.newsfeed.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreatePostDto {

    private final String title;

    private final String content;

    private final String username;
}

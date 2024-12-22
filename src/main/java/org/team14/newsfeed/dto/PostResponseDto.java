package org.team14.newsfeed.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    private final String username;
}

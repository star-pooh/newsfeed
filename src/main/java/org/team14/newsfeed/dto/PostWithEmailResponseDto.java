package org.team14.newsfeed.dto;

import lombok.Getter;

@Getter
public class PostWithEmailResponseDto {

    private final String title;
    private final String contents;
    private final String email;

    public PostWithEmailResponseDto(String title, String contents, String email) {
        this.title = title;
        this.contents = contents;
        this.email = email;
    }
}

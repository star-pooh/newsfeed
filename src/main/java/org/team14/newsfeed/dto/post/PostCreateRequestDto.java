package org.team14.newsfeed.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostCreateRequestDto {

    // 제목
    private final String title;

    // 내용
    private final String contents;

    // 사용자 이름
    private final String username;
}

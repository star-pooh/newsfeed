package org.team14.newsfeed.dto.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class PostUpdateRequestDto {

    // 제목
    private final String title;

    // 내용
    private final String contents;
}

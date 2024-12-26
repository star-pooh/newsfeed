package org.team14.newsfeed.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class PostUpdateRequestDto {

  private final String title;

  private final String contents;

}

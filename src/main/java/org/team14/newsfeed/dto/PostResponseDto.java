package org.team14.newsfeed.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.team14.newsfeed.entity.Post;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    private final String username;

    private final String email;


    public static PostResponseDto toDto(Post post) {

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getUser().getUsername(), post.getUser().getEmail());

    }

}

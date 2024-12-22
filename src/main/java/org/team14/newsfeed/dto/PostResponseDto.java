package org.team14.newsfeed.dto;


import lombok.Getter;
import org.team14.newsfeed.entity.Post;

@Getter
//@RequiredArgsConstructor
public class PostResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    private final String username;

    public PostResponseDto(Long id, String title, String contents, String username) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.username = username;
    }

    public static PostResponseDto toDto(Post post) {

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getUser().getEmail());

    }

}

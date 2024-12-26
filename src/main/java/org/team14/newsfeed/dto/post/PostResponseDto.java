package org.team14.newsfeed.dto.post;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.team14.newsfeed.entity.Post;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {

    // 뉴스 피드 ID
    private final Long id;

    // 제목
    private final String title;

    // 내용
    private final String contents;

    // 사용자 이름
    private final String username;

    // 이메일
    private final String email;

    public static PostResponseDto toDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContents(),
                post.getUser().getUsername(),
                post.getUser().getEmail()
        );
    }
}

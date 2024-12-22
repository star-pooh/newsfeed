package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.entity.Post;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.repository.PostRepository;
import org.team14.newsfeed.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * @param title    제목
     * @param contents 내용
     * @param username 사용자 이름
     * @return
     */
    public PostResponseDto createPost(String title, String contents, String username) {

        User findUser = userRepository.findUserByUsernameOrElseThrow(username);

        Post post = new Post(title, contents);
        post.setUser(findUser);

        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getUser().getUsername());
    }
}

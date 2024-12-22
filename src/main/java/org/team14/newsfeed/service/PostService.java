package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.entity.Post;
import org.team14.newsfeed.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(String title, String contents, String username) {

        Post findUser = postRepository.findPostByUsernameOrElseThrow(username);

        Post post = new Post(title, contents);
        post.setUser(findUser);

        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getUser.getUsername());
    }
}

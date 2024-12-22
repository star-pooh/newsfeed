package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(String title, String content, String username) {
        return null;
    }
}

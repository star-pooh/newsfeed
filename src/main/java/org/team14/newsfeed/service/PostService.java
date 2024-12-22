package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.dto.PostWithEmailResponseDto;
import org.team14.newsfeed.entity.Post;
import org.team14.newsfeed.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(String title, String contents, String username) {
        return null;
    }

    public List<PostResponseDto> findAll() {

        return postRepository.findAll()
                .stream()
                .map(PostResponseDto::toDto)
                .toList();

    }

    public PostWithEmailResponseDto findById(Long id) {

        Post findPost = postRepository.findByIdOrElseThrow(id);
        User creator = (User) findPost.getUser();

        return new PostWithEmailResponseDto(findPost.getTitle(), findPost.getContents(), creator.getUsername());

    }

    public void delete(Long id) {

        Post findPost = postRepository.findByIdOrElseThrow(id);

        postRepository.delete(findPost);

    }
}

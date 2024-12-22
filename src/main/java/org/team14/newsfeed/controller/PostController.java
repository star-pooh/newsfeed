package org.team14.newsfeed.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team14.newsfeed.dto.CreatePostDto;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.service.PostService;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> save(@RequestBody CreatePostDto requestDto) {

        PostResponseDto post = postService.createPost(
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getUsername()
        );
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

}

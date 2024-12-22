package org.team14.newsfeed.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team14.newsfeed.dto.CreatePostDto;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.dto.PostWithEmailResponseDto;
import org.team14.newsfeed.service.PostService;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> save(@RequestBody CreatePostDto requestDto) {

        PostResponseDto post = postService.createPost(
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getUsername()
        );
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll() {

        List<PostResponseDto> postResponseDtoList = postService.findAll();

        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);

    }

    //특정 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostWithEmailResponseDto> findById(@PathVariable Long id) {

        PostWithEmailResponseDto findWithEmailResponseDto = postService.findById(id);

        return new ResponseEntity<>(findWithEmailResponseDto, HttpStatus.OK);

    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deldte(@PathVariable Long id) {
        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

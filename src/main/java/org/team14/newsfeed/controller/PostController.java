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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /**
     * @param dto 게시글 작성에 필요한 요청 데이터
     * @return
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> save(@RequestBody CreatePostDto dto) {

        PostResponseDto post = postService.createPost(
                dto.getTitle(),
                dto.getContents(),
                dto.getUsername()
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

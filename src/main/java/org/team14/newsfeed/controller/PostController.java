package org.team14.newsfeed.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team14.newsfeed.dto.CreatePostDto;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.dto.PostUpdateRequestDto;
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
    public ResponseEntity<List<PostResponseDto>> findPostsByOptionalFilters(@RequestParam(required = false) String email,
                                                                            @RequestParam(required = false) String name) {


        List<PostResponseDto> postResponseDtoList = postService.findPostsByOptionalFilters(email, name);

        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);

    }

    // 포스트 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto updateRequestDto) {
        PostResponseDto postResponseDto = postService.updatePost(id, updateRequestDto);

        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }


    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

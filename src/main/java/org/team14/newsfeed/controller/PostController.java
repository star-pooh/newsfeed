package org.team14.newsfeed.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.team14.newsfeed.dto.post.PostCreateRequestDto;
import org.team14.newsfeed.dto.post.PostResponseDto;
import org.team14.newsfeed.dto.post.PostUpdateRequestDto;
import org.team14.newsfeed.exception.CustomException;
import org.team14.newsfeed.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 뉴스 피드 생성
     *
     * @param dto 뉴스 피드 작성에 필요한 요청 데이터
     * @return 생성된 뉴스 피드 정보
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> save(@RequestBody PostCreateRequestDto dto) {
        PostResponseDto post = postService.createPost(
                dto.getTitle(),
                dto.getContents(),
                dto.getUsername()
        );
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    /**
     * 뉴스 피드 조회
     *
     * @param email 이메일
     * @param name  사용자 이름
     * @return 조회된 뉴스 피드 정보
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findPostsByOptionalFilters(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name) {
        List<PostResponseDto> postResponseDtoList = postService.findPostsByOptionalFilters(email, name);

        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);

    }

    /**
     * 뉴스 피드 수정
     *
     * @param id               뉴스 피드 ID
     * @param updateRequestDto 뉴스 피드 수정에 필요한 요청 데이터
     * @param request          HttpServletRequest
     * @return 수정된 뉴스 피드 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @RequestBody PostUpdateRequestDto updateRequestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            PostResponseDto postResponseDto = postService.updatePost(id, token, updateRequestDto);

            return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다.");
        }
    }

    /**
     * 뉴스 피드 삭제
     *
     * @param id      뉴스 피드 ID
     * @param request HttpServletRequest
     * @return 삭제 결과
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            postService.delete(id, token);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

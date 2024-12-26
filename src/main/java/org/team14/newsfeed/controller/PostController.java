package org.team14.newsfeed.controller;


import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team14.newsfeed.dto.post.CreatePostDto;
import org.team14.newsfeed.dto.post.PostResponseDto;
import org.team14.newsfeed.dto.post.PostUpdateRequestDto;
import org.team14.newsfeed.exception.CustomException;
import org.team14.newsfeed.service.PostService;


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

    /**
     * @param email
     * @param name
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findPostsByOptionalFilters(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name) {

        List<PostResponseDto> postResponseDtoList = postService.findPostsByOptionalFilters(email,
                name);

        return new ResponseEntity<>(postResponseDtoList, HttpStatus.OK);

    }

    /**
     * @param id
     * @param updateRequestDto << 타이틀과 제목을 한번에
     * @param request
     * @return
     */
    // 포스트 수정
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
     * @param id
     * @return
     */
    //삭제
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

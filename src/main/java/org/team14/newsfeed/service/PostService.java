package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team14.newsfeed.dto.post.PostResponseDto;
import org.team14.newsfeed.dto.post.PostUpdateRequestDto;
import org.team14.newsfeed.entity.Post;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.CustomException;
import org.team14.newsfeed.repository.PostRepository;
import org.team14.newsfeed.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final TokenService tokenService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 뉴스 피드 생성
     *
     * @param title    제목
     * @param contents 내용
     * @param username 사용자 이름
     * @return 생성된 뉴스 피드 정보
     */
    public PostResponseDto createPost(String title, String contents, String username) {
        User findUser = userRepository.findByUsername(username).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, "사용자 이름이 없는 사용자를 찾을 수 없습니다 : " + username));

        Post post = Post.of(title, contents, findUser);
        postRepository.save(post);

        return PostResponseDto.toDto(post);
    }

    /**
     * 뉴스 피드 조회
     *
     * @param email 사용자 이메일
     * @param name  사용자 이름
     * @return 조회된 뉴스 피드 정보;
     */
    public List<PostResponseDto> findPostsByOptionalFilters(String email, String name) {

        List<Post> posts;

        if (email == null && name == null) {
            // 아무 필터도 존재하지 않음.
            //전체 조회
            posts = postRepository.findAll();

        } else if (email != null && name == null) {

            // 이메일 필터가 존재할 때. List<Post>
            // 특정 이메일 조회
            posts = postRepository.findAllByUser_Email(email);

        } else if (email == null && name != null) {

            // 작성자명 필터가 존재할 때.
            // 작성자명 (동명이인 포함) 조회
            posts = postRepository.findAllByUser_Username(name);

        } else {
            // 이메일, 작성자명 필터가 존재할 때.
            //예외처리
            posts = postRepository.findAllByUser_EmailAndUser_Username(email, name);
        }

        if (posts.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "포스트가 없습니다.");
        }

        //포스트를 리스트형태로 변환
        return posts.stream()
                .map(PostResponseDto::toDto)
                .toList();
    }

    /**
     * 뉴스 피드 수정
     *
     * @param id               뉴스 피드 ID
     * @param updateRequestDto 수정에 필요한 요청 데이터
     * @param token            JWT 토큰
     * @return 수정된 뉴스 피드 정보
     */
    @Transactional
    public PostResponseDto updatePost(Long id, String token, PostUpdateRequestDto updateRequestDto) {
        String emailFromToken = tokenService.extractEmailFromToken(token);

        //포스트를 찾기
        Post findPost = postRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 이메일 또는 이름 = " + id));

        // 사용자가 생성한 포스트인지 확인
        if (!findPost.getUser().getEmail().equals(emailFromToken)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        findPost.updateTitleAndContents(updateRequestDto.getTitle(), updateRequestDto.getContents());

        return PostResponseDto.toDto(findPost);
    }

    /**
     * 뉴스 피드 삭제
     *
     * @param id    뉴스 피드 ID
     * @param token JWT 토큰
     */
    public void delete(Long id, String token) {
        String tokenFromEmail = tokenService.extractEmailFromToken(token);

        Post findPost = postRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 이메일 또는 이름 = " + id));

        if (!findPost.getUser().getEmail().equals(tokenFromEmail)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        postRepository.delete(findPost);
    }
}

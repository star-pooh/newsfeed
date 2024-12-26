package org.team14.newsfeed.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.dto.PostUpdateRequestDto;
import org.team14.newsfeed.entity.Post;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.CustomException;
import org.team14.newsfeed.repository.PostRepository;
import org.team14.newsfeed.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    /**
     * @param title    제목
     * @param contents 내용
     * @param username 사용자 이름
     * @return
     */
    public PostResponseDto createPost(String title, String contents, String username) {

        User findUser = userRepository.findUserByUsernameOrElseThrow(username);
        Post post = Post.of(title, contents, findUser);
        post.setUser(findUser);

        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(),
                post.getUser().getUsername(), post.getUser().getEmail());

    }


    /*
        1. 게시글 조회
        2. 필터(작성자 이메일, 작성자명) 사용
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

    //포스트를 수정하는 메서드
    @Transactional
    public PostResponseDto updatePost(Long Id, PostUpdateRequestDto updateRequestDto) {

        //포스트를 찾기
        Post findPost = postRepository.findByIdOrElseThrow(Id);
        //타이틀이 널이 아닌 경우 타이틀 수정
        if (updateRequestDto.getTitle() != null) {
            findPost.setTitle(updateRequestDto.getTitle());

        }
        //내용이 널이 아닌 경우 널 수정
        if (updateRequestDto.getContents() != null) {
            findPost.setContents(updateRequestDto.getContents());
        }
        //둘다 널이 아닌 경우 둘다 수정되게 만듦

        return new PostResponseDto(findPost.getId(), findPost.getTitle(), findPost.getContents(),
                findPost.getUser().getUsername(), findPost.getUser().getEmail());
    }


    //삭제 메서드
    public void delete(Long Id) {
        //포스트를 찾고

        Post findPost = postRepository.findByIdOrElseThrow(Id);

        // 이후 삭제
        postRepository.delete(findPost);
    }


}

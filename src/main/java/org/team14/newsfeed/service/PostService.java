package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.dto.PostResponseDto;
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
            posts = postRepository.findAllByUser_Name(name);

        } else {
            // 이메일, 작성자명 필터가 존재할 때.
            //예외처리
            posts = postRepository.findAllByUser_EmailAndUser_Name(email, name);

        }

        if (posts.isEmpty()){
            throw new CustomRepositoryException(getClass().getSimpleName(), HttpStatus.NOT_FOUND, "작성하고 싶은 에러 메시지");
        }

        return posts.stream()
                .map(PostResponseDto::toDto)
                .toList();
    }

    public void delete(Long id) {

        Post findPost = postRepository.findByIdOrElseThrow(id);

        postRepository.delete(findPost);

    }


}

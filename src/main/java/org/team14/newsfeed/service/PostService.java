package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.dto.PostResponseDto;
import org.team14.newsfeed.dto.PostWithEmailResponseDto;
import org.team14.newsfeed.entity.Post;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.repository.PostRepository;
import org.team14.newsfeed.repository.UserRepository;

import java.util.List;

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

        User findUser = userRepository.findByUsernameOrElseThrow(username);
        Post post = Post.of(title, contents, findUser);
        post.setUser(findUser);

        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getUser().getUsername());

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

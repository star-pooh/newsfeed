package org.team14.newsfeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 이메일 또는 이름 = " + id));
    }


    List<Post> findAllByUser_Email(String email);

    List<Post> findAllByUser_Name(String name);

    List<Post> findAllByUser_EmailAndUser_Name(String email, String name);



}

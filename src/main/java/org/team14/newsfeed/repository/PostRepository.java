package org.team14.newsfeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team14.newsfeed.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //이메일로 적힌 모든 포스트를 리스트형태로 보여주기 위함
    List<Post> findAllByUser_Email(String email);

    //이름으로 적힌 모든 포스트를 리스트형태로 보여주기 위함(동명이인 포함)
    List<Post> findAllByUser_Username(String name);

    List<Post> findAllByUser_EmailAndUser_Username(String email, String name);
}

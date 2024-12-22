package org.team14.newsfeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team14.newsfeed.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}

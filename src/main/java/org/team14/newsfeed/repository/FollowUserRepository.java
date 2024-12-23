package org.team14.newsfeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team14.newsfeed.entity.FollowUser;

public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {
}

package org.team14.newsfeed.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team14.newsfeed.entity.FollowUser;
import org.team14.newsfeed.entity.User;

public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {

    boolean existsByFollowingUserAndFollowedUser(User following, User followed);

    Optional<FollowUser> findByFollowingUserAndFollowedUser(User followUser, User followeduser);

    void deleteByFollowingUserAndFollowedUser(User following, User followed);


}

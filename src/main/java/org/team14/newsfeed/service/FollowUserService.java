package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.entity.FollowUser;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.repository.FollowUserRepository;
import org.team14.newsfeed.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class FollowUserService {

    private final FollowUserRepository followUserRepository;
    private final UserRepository userRepository;

    public void follow(String following, String followed) {
        User followedUser = userRepository.findUserByEmailOrElseThrow(followed);
        User followingUser = userRepository.findUserByEmailOrElseThrow(following);

        if (followUserRepository.existsByFollowingAndFollowed(followingUser, followedUser)) {
            throw new IllegalArgumentException("이미 팔로우한 사람입니다.");
        }

        FollowUser follow = FollowUser.createFollowRelationship(followingUser, followedUser);
        followUserRepository.save(follow);
    }


}

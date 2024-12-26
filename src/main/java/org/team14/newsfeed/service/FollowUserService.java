package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team14.newsfeed.entity.FollowUser;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.CustomException;
import org.team14.newsfeed.repository.FollowUserRepository;
import org.team14.newsfeed.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowUserService {
    private final FollowUserRepository followUserRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    /**
     * 대상 사용자를 팔로우
     *
     * @param token    팔로우 하는 사용자의 이메일
     * @param followed 팔로우 대상(target)
     */
    public void follow(String token, String followed) {
        String following = tokenService.extractEmailFromToken(token);

        User followedUser = userRepository.findByUsernameOrElseThrow(followed);
        User followingUser = userRepository.findByUsernameOrElseThrow(following);

        if (following.equals(followed)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "자신을 팔로우 할 수 없습니다");
        }

        if (followUserRepository.existsByFollowingUserAndFollowedUser(followingUser, followedUser)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 팔로우한 사람입니다.");
        }

        FollowUser follow = FollowUser.createFollowRelationship(followingUser, followedUser);

        followUserRepository.save(follow);
    }

    /**
     * 대상 사용자를 팔로우 해제
     *
     * @param token    팔로우 하는 사용자의 이메일
     * @param followed 팔로우 대상(target)
     */
    @Transactional
    public void unfollow(String token, String followed) {
        String following = tokenService.extractEmailFromToken(token);

        User followedUser = userRepository.findByEmailOrElseThrow(followed);
        User followingUser = userRepository.findByEmailOrElseThrow(following);

        if (followed.equals(following)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "자신을 언팔로우 할 수 없습니다.");
        }

        if (followUserRepository.findByFollowingUserAndFollowedUser(followingUser, followedUser).isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "팔로우 관계가 존재하지 않습니다");
        }

        followUserRepository.deleteByFollowingUserAndFollowedUser(followingUser, followedUser);
    }
}
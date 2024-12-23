package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.repository.FollowUserRepository;

@Service
@RequiredArgsConstructor
public class FollowUserService {

    private final FollowUserRepository followUserRepository;


}

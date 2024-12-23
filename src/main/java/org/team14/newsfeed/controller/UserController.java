package org.team14.newsfeed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team14.newsfeed.dto.user.FollowUserCreateRequestDto;
import org.team14.newsfeed.dto.user.UserCreateRequestDto;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.service.FollowUserService;
import org.team14.newsfeed.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FollowUserService followUserService;

    /**
     * 사용자 생성 API
     *
     * @param dto 사용자 생성에 필요한 요청 데이터
     * @return 생성된 사용자 정보
     */
    @PostMapping
    public ResponseEntity<UserCreateResponseDto> createUser(@RequestBody UserCreateRequestDto dto) {
        UserCreateResponseDto userCreateResponseDto = this.userService.createUser(dto.getUsername(),
                dto.getEmail(),
                dto.getPassword());

        return new ResponseEntity<>(userCreateResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/follow")
    public ResponseEntity<Map<String, String>> follow(@RequestBody FollowUserCreateRequestDto dto) {

        followUserService.follow(dto.getFollowingUserEmail(), dto.getFollowedUserEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Follow Completed");

        return ResponseEntity.ok(response);
    }
}

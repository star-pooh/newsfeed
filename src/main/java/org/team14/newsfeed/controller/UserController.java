package org.team14.newsfeed.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team14.newsfeed.dto.user.UserUpdateRequestDto;
import org.team14.newsfeed.dto.user.UserUpdateResponseDto;
import org.team14.newsfeed.entity.User;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.dto.user.FollowUserCreateRequestDto;
import org.team14.newsfeed.dto.user.UserCreateRequestDto;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.service.FollowUserService;
import org.team14.newsfeed.service.UserService;

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
    public ResponseEntity<UserCreateResponseDto> createUser(
            @Valid @RequestBody UserCreateRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO : 전역 예외처리 추가 후 에러 메시지 내용 수정 필요
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "입력을 확인해주세요.");
        }

        this.userService.checkRegisteredUser(dto.getEmail());

        UserCreateResponseDto userCreateResponseDto = this.userService.createUser(dto.getUsername(),
                dto.getEmail(),
                dto.getPassword());


        return new ResponseEntity<>(userCreateResponseDto, HttpStatus.CREATED);
    }

    //TODO : session이 완성되면 세션을 통해 로그인되어있는 사람의 email 가져오는 로직으로 변경
    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody FollowUserCreateRequestDto dto) {

        followUserService.follow(dto.getFollowingUserEmail(), dto.getFollowedUserEmail());

        return ResponseEntity.ok("팔로우가 완료되었습니다.");
    }

    /**
     * 사용자 수정 API */
    @PutMapping("/{id}")
    public UserUpdateResponseDto updateUser(@PathVariable Long userId,
            @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        try {
            UserUpdateResponseDto updatedUser = userService.updateUser(userId, userUpdateRequestDto);
            return updatedUser;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 정보 수정 실패", e);
        }
    }
}

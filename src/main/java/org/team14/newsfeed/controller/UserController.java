package org.team14.newsfeed.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team14.newsfeed.dto.user.FollowUserCreateRequestDto;
import org.team14.newsfeed.dto.user.FollowUserDeleteRequestDto;
import org.team14.newsfeed.dto.user.UserCreateRequestDto;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.dto.user.UserReadResponseDto;
import org.team14.newsfeed.service.FollowUserService;
import org.team14.newsfeed.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final FollowUserService followUserService;

    /**
     * 사용자 생성 API
     *
     * @param dto 사용자 생성에 필요한 요청 데이터
     * @return 생성된 사용자 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<UserCreateResponseDto> createUser(
            @Valid @RequestBody UserCreateRequestDto dto) {
        this.userService.checkRegisteredUser(dto.getEmail());

        UserCreateResponseDto userCreateResponseDto = this.userService.createUser(dto.getUsername(),
                dto.getEmail(),
                dto.getPassword());

        return new ResponseEntity<>(userCreateResponseDto, HttpStatus.CREATED);
    }

    //TODO : session이 완성되면 세션을 통해 로그인되어있는 사람의 email 가져오는 로직으로 변경
    @PostMapping("/follow")
    public ResponseEntity<String> follow(@Valid @RequestBody FollowUserCreateRequestDto dto) {

        followUserService.follow(dto.getFollowingUserEmail(), dto.getFollowedUserEmail());

        return ResponseEntity.ok("팔로우가 완료되었습니다.");
    }

    /**
     * 사용자 조회 API
     *
     * @param username 사용자 이름
     * @param email    이메일
     * @return 조회된 사용자 정보
     */
    @GetMapping
    public ResponseEntity<List<UserReadResponseDto>> findUser(
            @RequestParam(required = false) String username,
            @Pattern(
                    regexp = "^[a-zA-Z0-9_]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                    message = "이메일 형식이 올바르지 않습니다.")
            @RequestParam(required = false)
            String email) {

        List<UserReadResponseDto> foundUserList = this.userService.findUser(username, email);

        return new ResponseEntity<>(foundUserList, HttpStatus.OK);
    }


    @DeleteMapping("/follow")
    public ResponseEntity<String> unFollow(@Valid @RequestBody FollowUserDeleteRequestDto dto) {

        followUserService.unfollow(dto.getFollowingUserEmail(), dto.getFollowedUserEmail());

        return ResponseEntity.ok("팔로우가 해제되었습니다");

    }
}

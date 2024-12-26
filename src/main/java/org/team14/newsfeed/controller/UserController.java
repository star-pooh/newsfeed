package org.team14.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.team14.newsfeed.dto.user.*;
import org.team14.newsfeed.exception.CustomException;
import org.team14.newsfeed.service.FollowUserService;
import org.team14.newsfeed.service.UserService;

import java.util.List;

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

    /**
     * 팔로우 API
     *
     * @param dto     팔로우에 필요한 요청 데이터
     * @param request HttpServletRequest
     * @return 결과 메시지
     */
    @PostMapping("/follow")
    public ResponseEntity<String> follow(@Valid @RequestBody FollowUserCreateRequestDto dto,
                                         HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            followUserService.follow(token, dto.getFollowedUserEmail());

            return ResponseEntity.ok("팔로우가 완료되었습니다.");
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다.");
        }
    }

    /**
     * 언팔로우 API
     *
     * @param dto     언팔로우에 필요한 요청 데이터
     * @param request HttpServletRequest
     * @return 결과 메시지
     */
    @DeleteMapping("/follow")
    public ResponseEntity<String> unFollow(
            @Valid @RequestBody FollowUserDeleteRequestDto dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            followUserService.unfollow(token, dto.getFollowedUserEmail());

            return ResponseEntity.ok("언팔로우가 완료되었습니다.");
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "JWT 토큰이 잘못되었습니다.");
        }
    }

    /**
     * 사용자 삭제 API
     *
     * @param dto 삭제에 필요한 요청 데이터
     * @return 삭제 결과
     */
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody @Valid UserDeleteRequestDto dto) {
        // 이메일과 비밀번호를 전달하여 삭제 처리
        userService.deleteUserByEmail(dto.getEmail(), dto.getPassword());

        return ResponseEntity.ok("사용자가 정상적으로 삭제되었습니다.");
    }

    /**
     * 사용자 복구 API
     *
     * @param id  사용자 ID
     * @param dto 복구에 필요한 요청 데이터
     * @return 복구 결과 메시지
     */
    @PostMapping("/{id}/restore")
    public ResponseEntity<String> restoreUser(
            @PathVariable Long id,
            @RequestBody @Valid UserDeleteRequestDto dto) {
        userService.restoreUser(id, dto.getPassword());

        return ResponseEntity.ok("사용자가 정상적으로 복구되었습니다.");
    }

    /**
     * 사용자 수정 API
     *
     * @param id                   사용자 ID
     * @param userUpdateRequestDto 수정에 필요한 요청 데이터
     * @return 수정된 사용자 정보
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserUpdateResponseDto updatedUser = userService.updateUser(id, userUpdateRequestDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
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
}

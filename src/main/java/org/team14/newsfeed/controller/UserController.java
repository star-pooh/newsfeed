package org.team14.newsfeed.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.dto.user.UserCreateRequestDto;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

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

    this.userService.isExistUser(dto.getEmail());

    UserCreateResponseDto userCreateResponseDto = this.userService.createUser(dto.getUsername(),
        dto.getEmail(),
        dto.getPassword());

    return new ResponseEntity<>(userCreateResponseDto, HttpStatus.CREATED);
  }
}

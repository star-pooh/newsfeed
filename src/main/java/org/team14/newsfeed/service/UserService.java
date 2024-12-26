package org.team14.newsfeed.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.config.PasswordEncoder;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.dto.user.UserUpdateRequestDto;
import org.team14.newsfeed.dto.user.UserUpdateResponseDto;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.CustomServiceException.PasswordMismatchException;
import org.team14.newsfeed.exception.CustomServiceException.ResourceNotFoundException;
import org.team14.newsfeed.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  /**
   * 등록된 사용자인지 확인
   *
   * @param email 이메일
   */
  public void checkRegisteredUser(String email) {
    User foundUser = this.userRepository.findByEmail(email).orElse(null);

    if (Objects.isNull(foundUser)) {
      return;
    }

    if (foundUser.isDeleted()) {
      log.error("[UserService.checkRegisteredUser] 탈퇴한 이메일로 가입 시도 : {}", email);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "탈퇴한 이메일로는 가입할 수 없습니다. email : " + email);
    } else {
      log.error("[UserService.checkRegisteredUser] 등록된 이메일로 가입 시도 : {}", email);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 등록된 이메일입니다. email : " + email);
    }
  }

  /**
   * 사용자 생성
   *
   * @param username 사용자 이름
   * @param email    이메일
   * @param password 비밀번호
   * @return 등록된 사용자 정보
   */
  public UserCreateResponseDto createUser(String username, String email, String password) {
    String encodedPassword = passwordEncoder.encode(password);
    User user = User.of(username, email, encodedPassword);

    User savedUser = this.userRepository.save(user);

    return UserCreateResponseDto.of(savedUser);
  }

  /**
   * 사용자 정보 수정
   * 이메일 변경 시 중복 확인을 하고, 비밀번호 변경 시 현재 비밀번호를 확인합니다.
   *
   * @param userId 사용자 ID
   * @param userUpdateRequestDto 사용자 업데이트 요청 DTO
   * @return 업데이트된 사용자 정보
   */
  @Transactional
  public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
    // 사용자 존재 여부 확인
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

    // 사용자 이름 변경
    user.updateUsername(userUpdateRequestDto.getUsername());

    // 이메일 변경 시 중복 확인
    if (!user.getEmail().equals(userUpdateRequestDto.getEmail())) {
      checkRegisteredUser(userUpdateRequestDto.getEmail());
      user.updateEmail(userUpdateRequestDto.getEmail());
    }

    /**
     * 사용자 비밀번호 수정
     * 현재 비밀번호의 일치 여부를 확인하고 새 비밀번호를 암호화하여 저장
     *
     * @param currentPassword 현재 비밀번호
     * @param newPassword     새로운 비밀번호
     * @param passwordEncoder 비밀번호 암호화 도구
     */
    if (userUpdateRequestDto.getNewPassword() != null) {
      if (!passwordEncoder.matches(userUpdateRequestDto.getCurrentPassword(), user.getPassword())) {
        // 비밀번호가 일치하지 않으면 예외 발생
        throw new PasswordMismatchException("현재 비밀번호가 올바르지 않습니다.");
      }

      user.changePassword(passwordEncoder.encode(userUpdateRequestDto.getNewPassword()));
    }

    return UserUpdateResponseDto.of(user);
  }

}

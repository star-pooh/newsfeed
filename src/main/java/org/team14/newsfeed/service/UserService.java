package org.team14.newsfeed.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.config.PasswordEncoder;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.dto.user.UserDeleteRequestDto;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.ResourceNotFoundException;
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
   * 사용자 삭제
   */
  public void deleteUser(Long userId, String password) {
    // 사용자 조회
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. userId: " + userId));

    // 이미 삭제된 사용자 여부 확인
    if (user.isDeleted()) {
      log.error("[UserService.deleteUser] 이미 탈퇴한 사용자입니다. userId: {}", userId);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 탈퇴한 사용자입니다.");
    }

    // 비밀번호 검증
    if (!passwordEncoder.matches(password, user.getPassword())) {
      log.error("[UserService.deleteUser] 비밀번호가 일치하지 않습니다. userId: {}", userId);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }

    // 사용자 삭제 처리 (isDeleted = true 로 설정)
    user.setDeleted(true);

    // 변경 사항 저장
    userRepository.save(user);
  }

  // 사용자 복구
  public void restoreUser(Long userId, String password) {
    // 사용자 조회
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. userId: " + userId));

    // 비밀번호 검증
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }

    // 복구 가능 여부 확인 및 복구
    user.restore();

    // 변경 사항 저장
    userRepository.save(user);
  }

  // 이메일과 비밀번호로 삭제 처리
  public void deleteUserByEmail(String email, String password) {
    // 사용자 조회
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. email: " + email));

    // 이미 삭제된 사용자 여부 확인
    if (user.isDeleted()) {
      log.error("[UserService.deleteUserByEmail] 이미 탈퇴한 사용자입니다. email: {}", email);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 탈퇴한 사용자입니다.");
    }

    // 비밀번호 검증
    if (!passwordEncoder.matches(password, user.getPassword())) {
      log.error("[UserService.deleteUserByEmail] 비밀번호가 일치하지 않습니다. email: {}", email);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }

    // 사용자 삭제 처리
    user.setDeleted(true);
    userRepository.save(user);
  }

}

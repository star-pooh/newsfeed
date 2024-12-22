package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.config.PasswordEncoder;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  /**
   * 사용자 생성
   *
   * @param username 사용자 이름
   * @param email    이메일
   * @param password 비밀번호
   * @return
   */
  public UserCreateResponseDto createUser(String username, String email, String password) {
    String encodedPassword = passwordEncoder.encode(password);
    User user = User.of(username, email, encodedPassword);

    User savedUser = this.userRepository.save(user);

    return UserCreateResponseDto.of(savedUser);
  }
}

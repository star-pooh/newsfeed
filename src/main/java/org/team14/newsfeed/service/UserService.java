package org.team14.newsfeed.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.config.UserPasswordEncoder;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.dto.user.UserReadResponseDto;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.CustomException;
import org.team14.newsfeed.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserPasswordEncoder userPasswordEncoder;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "이미 등록된 이메일입니다. email : " + email);
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
        String encodedPassword = userPasswordEncoder.encode(password);
        User user = User.of(username, email, encodedPassword);

        User savedUser = this.userRepository.save(user);

        return UserCreateResponseDto.of(savedUser);
    }


    public void checkAuthentication(String email, String password) {
        User foundUser = this.userRepository.findUserByEmailOrElseThrow(email);

        if (!userPasswordEncoder.matches(password, foundUser.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }

    public List<UserReadResponseDto> findUser(String username, String email) {
        return this.userRepository.findByUsernameAndEmail(username, email).stream()
                .map(UserReadResponseDto::of).toList();

    }
}

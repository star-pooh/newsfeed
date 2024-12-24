package org.team14.newsfeed.service;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.config.PasswordEncoder;
import org.team14.newsfeed.dto.user.UserCreateResponseDto;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.CustomServiceException;
import org.team14.newsfeed.repository.UserRepository;
import org.team14.newsfeed.token.JwtUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationProvider authenticationProvider;

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
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.of(username, email, encodedPassword);

        User savedUser = this.userRepository.save(user);

        return UserCreateResponseDto.of(savedUser);
    }

    public String authenticateUser(String email, String password, HttpServletResponse response) {
        try {
            // AuthenticationProvider를 통한 인증
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.createToken(email);
            response.addHeader("Authorization", "Bearer " + token);
            return token;
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw new CustomServiceException(
                    getClass().getSimpleName(),
                    HttpStatus.UNAUTHORIZED,
                    "인증에 실패했습니다."
            );
        }
    }
}

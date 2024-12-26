package org.team14.newsfeed.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.team14.newsfeed.dto.login.LoginRequestDto;
import org.team14.newsfeed.dto.login.TokenResponseDto;
import org.team14.newsfeed.jwt.JwtFilter;
import org.team14.newsfeed.jwt.TokenProvider;
import org.team14.newsfeed.service.BlackListService;
import org.team14.newsfeed.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider; // JWT 토큰 생성 및 관리
    private final AuthenticationManagerBuilder authenticationManagerBuilder; // 인증 처리
    private final UserService userService;
    private final BlackListService blackListService;


    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        // 사용자 인증을 위해 이메일과 비밀번호를 확인
        this.userService.checkAuthentication(dto.getEmail(), dto.getPassword());

        // 이메일과 비밀번호로 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getEmail(), dto.getPassword());

        // 생성된 인증 토큰으로 실제 인증 처리
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        // 인증된 사용자 정보를 SecurityContext에 저장하여 이후 요청에서 사용할 수 있도록 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        // 응답 헤더에 JWT 토큰 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(TokenResponseDto.of(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * 사용자 로그아웃을 처리
     *
     * @param request Http 요청 객체, Authorization 헤더에서 jwt 토큰 추출하기위함
     * @return ResponseEntity<String> 로그아웃 처리 결과를 반환
     */

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        String token = request.getHeader(JwtFilter.AUTHORIZATION_HEADER);

        if (token != null && token.startsWith("Bearer ")) {
            try {
                SecurityContextHolder.clearContext();
                blackListService.addToBlacklist(token);
                blackListService.getBlacklistedTokens();
                return ResponseEntity.ok("로그아웃 되었습니다");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("로그아웃 처리 중 오류가 발생하였습니다.");
            }
        }
        return ResponseEntity.badRequest().body("유효한 토큰이 없습니다.");
    }
}

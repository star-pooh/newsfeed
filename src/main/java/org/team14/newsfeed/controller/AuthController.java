package org.team14.newsfeed.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.team14.newsfeed.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider; // JWT 토큰 생성 및 관리
    private final AuthenticationManagerBuilder authenticationManagerBuilder; // 인증 처리
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        // 사용자 인증을 위해 이메일과 비밀번호를 확인
        this.userService.checkAuthentication(dto.getEmail(), dto.getPassword());

        // 이메일과 비밀번호로 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // 생성된 인증 토큰으로 실제 인증 처리
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 인증된 사용자 정보를 SecurityContext에 저장하여 이후 요청에서 사용할 수 있도록 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        // 응답 헤더에 JWT 토큰 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(TokenResponseDto.of(jwt), httpHeaders, HttpStatus.OK);
    }
}

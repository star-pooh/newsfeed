package org.team14.newsfeed.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    // JWT 토큰 생성 및 검증
    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        /*
          JwtFilter가 먼저 실행되어야 하는 이유는?
          JWT 토큰을 추출하고 검증한 사용자 인증 정보를 SecurityContext에 설정
          이렇게 설정된 사용자 인증 정보는 이후의 보안 필터들이 요청을 처리할 때 사용

          UsernamePasswordAuthenticationFilter는 form 기반 인증(일반적인 로그인 화면)을 위한 필터인데,
          JWT 인증이 먼저 이루어지지 않으면 인증을 처리할 수 없기 때문에 순서를 변경
         */
        httpSecurity.addFilterBefore(
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
    }
}

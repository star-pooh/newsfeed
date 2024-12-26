package org.team14.newsfeed.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.team14.newsfeed.jwt.JwtAccessDeniedHandler;
import org.team14.newsfeed.jwt.JwtAuthenticationEntryPoint;
import org.team14.newsfeed.jwt.JwtSecurityConfig;
import org.team14.newsfeed.jwt.TokenProvider;

@EnableWebSecurity // Spring Security의 기본 설정 활성화
@EnableMethodSecurity // 메소드 수준에서 보안 제어 활성화
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // JWT 토큰 생성 및 검증
    private final TokenProvider tokenProvider;
    // CORS 설정을 처리하는 필터
    private final CorsFilter corsFilter;
    // 인증 실패 시 동작 정의
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // 접근 권한이 없을 때의 동작 정의
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                /*
                  CSRF(Cross-Site Request Forgery) :
                  사용자가 의도하지 않은 요청을 악의적인 웹사이트를 통해 전송하게 하는 공격

                  일반적으로는 CSRF 토큰을 생성하고 검증하는 방식으로 보호를 하지만
                  JWT 방식은 서버에서 세션을 유지하지 않기 때문에 CSRF 보호를 비활성화
                  (JWT 토큰이 요청 헤더에 포함되어 서버와의 통신이 이루어지기 때문에)
                 */
                // CSRF 보호를 비활성화 (JWT 기반 인증을 사용하기 때문)
                .csrf(AbstractHttpConfigurer::disable)

                // CORS 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                // 인증 및 인가 예외 처리 정의
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))

                // 인증이 필요 없는 URL과 인증이 필요한 요청 정의
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/login", "/users/signup").permitAll()
                        .anyRequest().authenticated())

                // 세션이 아닌 JWT로 상태를 유지하기 위해 Stateless로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // JWT 관련 설정 적용
                .with(new JwtSecurityConfig(tokenProvider), customizer -> {
                });

        return httpSecurity.build();
    }
}

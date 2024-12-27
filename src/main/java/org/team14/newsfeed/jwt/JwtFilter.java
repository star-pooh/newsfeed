package org.team14.newsfeed.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {

    // HTTP 요청 헤더에서 JWT 토큰을 찾을 키
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // JWT 토큰 생성 및 검증
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        // 요청 헤더에서 JWT 토큰 및 URI 추출
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        // JWT 토큰이 존재하고 유효하다면, 인증 정보를 SecurityContext에 저장
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // JWT로부터 인증 정보 추출
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            // 인증 정보를 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 {} 인증 정보를 저장했습니다. URI : {}", authentication.getName(),
                    requestURI);
        } else {
            log.debug("유효한 JWT 토큰이 없습니다. URI : {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        // HTTP 요청에서 Authorization 헤더 추출
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        /*
          Bearer :
          '소지자'를 의미하는 단어로, 보통 Authorization 헤더에서 JWT 토큰이 전달 될 때 사용
          해당 토큰을 소지한 사람이 인증된 사용자라는 것을 나타내며, OAuth 2.0이나 JWT에서 사용

          ex)
          Authorization: Bearer <JWT_TOKEN>
         */
        // "Bearer "로 시작하는 경우 토큰을 반환, 아니면 null 반환
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

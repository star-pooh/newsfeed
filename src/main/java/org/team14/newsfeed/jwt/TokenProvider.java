package org.team14.newsfeed.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    // 권한 정보가 담길 클레임의 키
    private static final String AUTHORITIES_KEY = "auth";
    // JWT Signature에 사용할 비밀키
    private final String secret;
    // 토큰의 유효 기간(밀리초 단위)
    private final long tokenValidityInMilliseconds;
    // JWT Signature를 위한 Key 객체
    private Key key;

    public TokenProvider(
            // 설정 파일(application.properties)에서 값을 주입
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        // secret 키를 Base64로 디코딩하고, HMAC-SHA 알고리즘 키를 생성
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 인증 객체로부터 JWT 토큰 생성
     *
     * @param authentication 인증된 사용자 정보
     * @return 생성된 JWT 토큰 문자열
     */
    public String createToken(Authentication authentication) {
        // 인증 정보에서 권한을 가져와서 쉼표로 구분된 문자열로 반환
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간과 유효 기간을 기반으로 토큰 만료 시간 계산
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        // JWT 토큰 생성 및 서명
        return Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 이름을 Subject로 설정
                .claim(AUTHORITIES_KEY, authorities) // 권한 정보를 클레임에 추가
                .signWith(key, SignatureAlgorithm.HS512) // 비밀 키로 서명
                .setExpiration(validity) // 만료 시간 설정
                .compact(); // JWT 토큰 반환
    }

    /**
     * JWT 토큰에서 인증 정보를 추출하여 인증 객체로 변환
     *
     * @param token JWT 토큰 문자열
     * @return JWT에서 추출한 인증 객체
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody(); // JWT에서 클레임 정보를 추출

        // 클레임에서 권한 정보를 가져와 GrantedAuthority 객체로 변환
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * JWT 토큰의 유효성 검증
     *
     * @param token JWT 토큰 문자열
     * @return true : 유효한 토큰 / false : 유효하지 않은 토큰
     */
    public boolean validateToken(String token) {
        try {
            // 서명 검증 및 클레임 파싱 시 오류가 발생하지 않으면 유효한 토큰으로 간주
            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
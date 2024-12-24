package org.team14.newsfeed.token;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String createToken(String username) {
        JwtClaimsSet claims = JwtClaimsSet.builder().issuer("newsfeed-app").issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.HOURS)).subject(username)
                .claim("username", username).build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        log.info("Created token: {}", token);  // 생성된 토큰 로깅
        return token;
    }

    public boolean validateToken(String token) {
        try {
            log.info("Validating token: {}", token);  // 검증할 토큰 로깅
            Jwt jwt = jwtDecoder.decode(token);
            log.info("Token decoded successfully. Subject: {}", jwt.getSubject());
            return true;
        } catch (Exception e) {
            log.error("Token validation failed with exception: ", e);  // 상세 에러 로깅
            return false;
        }
    }

    public Jwt getUserInfoFromToken(String token) {
        try {
            log.info("Extracting user info from token: {}", token);
            Jwt jwt = jwtDecoder.decode(token);
            log.info("User info extracted. Subject: {}", jwt.getSubject());
            return jwt;
        } catch (Exception e) {
            log.error("Failed to extract user info from token: ", e);
            throw new RuntimeException("토큰에서 사용자 정보를 추출할 수 없습니다.", e);
        }
    }
}

package org.team14.newsfeed.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.exception.CustomException;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${jwt.secret}")
    private String key;

    public String extractEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "토큰에서 이메일을 추출하는데 실패했습니다.");
        }
    }
}

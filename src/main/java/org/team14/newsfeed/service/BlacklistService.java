package org.team14.newsfeed.service;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlacklistService {

    // 메모리 기반 블랙리스트
    private Set<String> blacklistedTokens = new HashSet<>();

    // 토큰을 블랙리스트에 추가하는 메서드
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlacklisted(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        boolean isBlacklisted = blacklistedTokens.contains(token);
        log.info("Token check - Is blacklisted: {}", isBlacklisted);
        return isBlacklisted;
    }

    public Set<String> getBlacklistedTokens() {
        return new HashSet<>(blacklistedTokens);
    }
}

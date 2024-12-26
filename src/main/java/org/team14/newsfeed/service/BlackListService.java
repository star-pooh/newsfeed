package org.team14.newsfeed.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class BlackListService {

    // 메모리 기반 블랙리스트
    private Set<String> blacklistedTokens = new HashSet<>();

    // 토큰을 블랙리스트에 추가하는 메서드
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    public Set<String> getBlacklistedTokens() {
        return new HashSet<>(blacklistedTokens);
    }
}
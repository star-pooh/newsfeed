package org.team14.newsfeed.config;

import java.security.SecureRandom;
import java.util.Base64;

public class GenerateKey {

    public static void main(String[] args) {
        // 32바이트(256비트) 랜덤 키 생성
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);

        // Base64로 인코딩
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated Key: " + encodedKey);
    }

}

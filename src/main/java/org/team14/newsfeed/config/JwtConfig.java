package org.team14.newsfeed.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;


@Configuration
@Slf4j
public class JwtConfig {

    private static final String STATIC_KEY_ID = "static-key-2023";  // 고정된 키 ID
    private static KeyPair KEY_PAIR;  // 정적 키 쌍
    private static RSAKey RSA_KEY;    // 정적 RSA 키

    public JwtConfig() {
        if (KEY_PAIR == null) {
            KEY_PAIR = generateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) KEY_PAIR.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) KEY_PAIR.getPrivate();

            RSA_KEY = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(STATIC_KEY_ID)
                    .build();

            log.info("RSA Key initialized with ID: {}", STATIC_KEY_ID);
        }
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWKSet jwkSet = new JWKSet(RSA_KEY);
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(jwkSet));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey) KEY_PAIR.getPublic())
                .build();
    }

    private KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate RSA key pair", ex);
        }
    }
}


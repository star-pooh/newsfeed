package org.team14.newsfeed.config;

import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements
        org.springframework.security.crypto.password.PasswordEncoder {

    private final PasswordEncoder passwordEncoder;

    public CustomPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword.toString(), encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}

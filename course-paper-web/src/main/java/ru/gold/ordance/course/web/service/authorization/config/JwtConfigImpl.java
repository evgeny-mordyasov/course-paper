package ru.gold.ordance.course.web.service.authorization.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigImpl implements JwtConfig {
    private final String secret;
    private final Long expirationMs;

    public JwtConfigImpl(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.expirationMs}") Long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public Long getExpirationMs() {
        return expirationMs;
    }
}

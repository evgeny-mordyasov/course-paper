package ru.gold.ordance.course.web.service.web.authorization.config;

public class JwtConfigImpl implements JwtConfig {
    private final String secret;
    private final Long expirationMs;

    public JwtConfigImpl(String secret, Long expirationMs) {
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

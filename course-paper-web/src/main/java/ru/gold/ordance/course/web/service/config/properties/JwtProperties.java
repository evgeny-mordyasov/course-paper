package ru.gold.ordance.course.web.service.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "module-properties.jwt")
public class JwtProperties {
    private final String secret;
    private final Long expirationMs;

    public JwtProperties(String secret, Long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String getSecret() {
        return secret;
    }

    public Long getExpirationMs() {
        return expirationMs;
    }
}

package ru.gold.ordance.course.web.service.authorization.config;

public interface JwtConfig {
    String getSecret();

    Long getExpirationMs();
}

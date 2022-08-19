package ru.gold.ordance.course.web.service.web.authorization.config;

public interface JwtConfig {
    String getSecret();

    Long getExpirationMs();
}

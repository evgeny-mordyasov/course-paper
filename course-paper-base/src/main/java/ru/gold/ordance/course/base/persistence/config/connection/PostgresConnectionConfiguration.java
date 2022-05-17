package ru.gold.ordance.course.base.persistence.config.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostgresConnectionConfiguration implements DbConnectionConfiguration {
    private final String url;

    private final String username;

    private final String password;

    public PostgresConnectionConfiguration(@Value("${persistence.postgres.url:}") String url,
                                           @Value("${persistence.postgres.username:}") String username,
                                           @Value("${persistence.postgres.password:}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }
}

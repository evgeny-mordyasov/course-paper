package ru.gold.ordance.course.base.persistence.config.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class H2ConnectionConfiguration implements DbConnectionConfiguration {
    private final String url;

    private final String username;

    private final String password;

    public H2ConnectionConfiguration(@Value("${persistence.h2.url:}") String url,
                                     @Value("${persistence.h2.username:}") String username,
                                     @Value("${persistence.h2.password:}") String password) {
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

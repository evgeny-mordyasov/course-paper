package ru.gold.ordance.course.base.persistence.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gold.ordance.course.base.persistence.config.PostgresConnectionConfiguration;

@Configuration
public class ConnectionConfiguration {

    @Value("${persistence.postgres.url}")
    private String url;

    @Value("${persistence.postgres.username}")
    private String username;

    @Value("${persistence.postgres.password}")
    private String password;

    @Bean
    public PostgresConnectionConfiguration psConnectionConfig() {
        return new PostgresConnectionConfiguration(url, username, password);
    }
}

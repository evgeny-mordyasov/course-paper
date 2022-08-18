package ru.gold.ordance.course.base.persistence.config.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gold.ordance.course.base.persistence.config.dialect.DbDialect;

@Configuration
public class ConnectionConfiguration {
    private final DbDialect dbDialect;
    private final String url;
    private final String username;
    private final String password;

    public ConnectionConfiguration(@Value("${persistence.dialect}") DbDialect dbDialect,
                                   @Value("${persistence.postgres.url}") String url,
                                   @Value("${persistence.postgres.username}") String username,
                                   @Value("${persistence.postgres.password}") String password) {
        this.dbDialect = dbDialect;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Bean
    public DbConnectionConfiguration connectionConfig(PostgresConnectionConfiguration ps, H2ConnectionConfiguration h2) {
        return dbDialect == DbDialect.POSTGRES ? ps : h2;
    }

    @Bean
    public PostgresConnectionConfiguration psConnectionConfig() {
        return new PostgresConnectionConfiguration(url, username, password);
    }

    @Bean
    public H2ConnectionConfiguration h2ConnectionConfig() {
        return new H2ConnectionConfiguration(url, username, password);
    }
}

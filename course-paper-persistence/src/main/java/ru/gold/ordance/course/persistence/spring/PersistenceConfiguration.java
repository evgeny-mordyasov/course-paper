package ru.gold.ordance.course.persistence.spring;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gold.ordance.course.persistence.config.PostgresConnectionConfiguration;
import ru.gold.ordance.course.persistence.spring.annotation.JpaContext;

import javax.sql.DataSource;

@JpaContext
@Configuration
public class PersistenceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "persistence.postgres")
    public PostgresConnectionConfiguration psConnectionConfig() {
        return new PostgresConnectionConfiguration();
    }

    @Bean
    public DataSource dataSource(PostgresConnectionConfiguration connectionConfig) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(connectionConfig.url());
        hikariDataSource.setUsername(connectionConfig.username());
        hikariDataSource.setPassword(connectionConfig.password());

        return hikariDataSource;
    }
}

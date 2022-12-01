package ru.gold.ordance.course.persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gold.ordance.course.persistence.config.properties.PostgresConnectionProperties;
import ru.gold.ordance.course.persistence.annotation.JpaContext;

import javax.sql.DataSource;

@JpaContext
@Configuration
@ConfigurationPropertiesScan
public class PersistenceConfiguration {

    @Bean
    public DataSource dataSource(PostgresConnectionProperties pgProp) {
        HikariDataSource hks = new HikariDataSource();
        hks.setJdbcUrl(pgProp.getUrl());
        hks.setUsername(pgProp.getUsername());
        hks.setPassword(pgProp.getPassword());

        return hks;
    }
}

package ru.gold.ordance.course.base.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("ru.gold.ordance.course.base.persistence")
@EntityScan("ru.gold.ordance.course.base.entity")
@ComponentScan("ru.gold.ordance.course.base.service")
public class ServiceConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }

    @Bean
    public DataSource dataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{ "localhost" });
        ds.setDatabaseName("cs");
        ds.setUser("postgres");
        ds.setPassword("postgres");

        return ds;
    }
}

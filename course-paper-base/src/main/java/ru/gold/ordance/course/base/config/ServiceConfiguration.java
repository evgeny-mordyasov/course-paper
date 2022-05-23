package ru.gold.ordance.course.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import ru.gold.ordance.course.base.persistence.config.PersistenceConfiguration;

@Configuration
@ComponentScan(value = {
        "ru.gold.ordance.course.base.service",
        "ru.gold.ordance.course.base.spring"})
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }
}

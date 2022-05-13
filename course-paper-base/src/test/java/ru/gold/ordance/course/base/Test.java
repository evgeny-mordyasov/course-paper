package ru.gold.ordance.course.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.gold.ordance.course.base.config.ServiceConfiguration;

@SpringBootApplication
@Import(value = { ServiceConfiguration.class })
public class Test {
    public static void main(String[] args) {
        SpringApplication.run(Test.class, args);
    }
}
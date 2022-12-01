package ru.gold.ordance.course.persistence.annotation;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.gold.ordance.course.persistence.repository.main.impl.RefreshedJpaRepositoryImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaRepositories(value = "ru.gold.ordance.course.persistence.repository", repositoryBaseClass = RefreshedJpaRepositoryImpl.class)
@EntityScan("ru.gold.ordance.course.persistence.entity")
public @interface JpaContext {
}

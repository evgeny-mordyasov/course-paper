package ru.gold.ordance.course.base.spring.annotation;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.gold.ordance.course.common.utils.PackageBeanContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaRepositories(PackageBeanContext.REPOSITORIES_PACKAGE)
@EntityScan(PackageBeanContext.ENTITIES_PACKAGE)
public @interface JpaContext {
}

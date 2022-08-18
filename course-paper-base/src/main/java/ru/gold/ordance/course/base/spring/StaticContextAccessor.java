package ru.gold.ordance.course.base.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.base.persistence.PersistenceHelper;

@Component
public class StaticContextAccessor {
    private static ApplicationContext context;

    private StaticContextAccessor(ApplicationContext ctx) {
        context = ctx;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (context == null) {
            throw new IllegalStateException("The ApplicationContext bean is not initialized.");
        }

        return context.getBean(clazz);
    }

    public static PersistenceHelper getPersistenceHelper() {
        return getBean(PersistenceHelper.class);
    }
}

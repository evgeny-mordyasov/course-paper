package ru.gold.ordance.course.base.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.base.utils.StorageHelper;

@Component
public class StaticContextAccessor {
    private static ApplicationContext context;

    public StaticContextAccessor(ApplicationContext ctx) {
        context = ctx;
    }

    public static <T> T getBean(Class<T> clazz) {
        if (context == null) {
            throw new IllegalStateException("The ApplicationContext bean is not initialized.");
        }

        return context.getBean(clazz);
    }

    public static StorageHelper getStorageHelper() {
        return getBean(StorageHelper.class);
    }
}

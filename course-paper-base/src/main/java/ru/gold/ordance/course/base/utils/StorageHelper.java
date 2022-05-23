package ru.gold.ordance.course.base.utils;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Objects;

@Component
public class StorageHelper {
    private final EntityManager manager;

    public StorageHelper(EntityManager manager) {
        this.manager = manager;
    }

    public <T> T findById(Class<T> clazz, Long id) {
        return manager.find(clazz, id);
    }

    public boolean existsById(Class<?> clazz, Long id) {
        return Objects.nonNull(findById(clazz, id));
    }
}

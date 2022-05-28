package ru.gold.ordance.course.base.utils;

import org.springframework.stereotype.Component;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Language;

import javax.persistence.EntityManager;
import java.util.Objects;

@Component
public class PersistenceHelper {
    private final EntityManager manager;

    public PersistenceHelper(EntityManager manager) {
        this.manager = manager;
    }

    public <T> T findById(Class<T> clazz, Long id) {
        return manager.find(clazz, id);
    }

    public boolean existsById(Class<?> clazz, Long id) {
        return Objects.nonNull(findById(clazz, id));
    }

    public String getLanguageNameById(Long id) {
        return findById(Language.class, id).getName();
    }

    public String getClassificationNameById(Long id) {
        return findById(Classification.class, id).getName();
    }
}

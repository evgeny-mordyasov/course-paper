package ru.gold.ordance.course.base.persistence;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;

import javax.persistence.EntityManager;
import java.util.Objects;

public class PersistenceHelper {
    private static EntityManager manager;

    public PersistenceHelper(EntityManager em) {
        manager = em;
    }

    public static <T> T findById(Class<T> clazz, Long entityId) {
        return manager.find(clazz, entityId);
    }

    public static boolean noExistsById(Class<?> clazz, Long entityId) {
        return Objects.isNull(findById(clazz, entityId));
    }

    public static boolean noExistsClassification(Long entityId) {
        return noExistsById(Classification.class, entityId);
    }

    public static boolean noExistsLanguage(Long entityId) {
        return noExistsById(Language.class, entityId);
    }

    public static boolean noExistsDocument(Long entityId) {
        return noExistsById(Document.class, entityId);
    }

    public static String getLanguageNameById(Long entityId) {
        return findById(Language.class, entityId).getName();
    }

    public static String getClassificationNameById(Long entityId) {
        return findById(Classification.class, entityId).getName();
    }
}

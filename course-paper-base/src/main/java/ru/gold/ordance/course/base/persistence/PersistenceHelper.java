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

    public static Classification getClassificationById(Long classificationId) {
        return findById(Classification.class, classificationId);
    }

    public static Language getLanguageById(Long languageId) {
        return findById(Language.class, languageId);
    }

    public static Document getDocumentById(Long documentId) {
        return findById(Document.class, documentId);
    }
}

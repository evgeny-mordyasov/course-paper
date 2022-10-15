package ru.gold.ordance.course.base.persistence;

import ru.gold.ordance.course.base.entity.AbstractEntity;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import javax.persistence.EntityManager;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PersistenceHelper {
    private static EntityManager manager;

    public PersistenceHelper(EntityManager em) {
        manager = em;
    }

    public static <T> T findById(Class<T> clazz, Long entityId) {
        Optional<T> entity = Optional.ofNullable(manager.find(clazz, entityId));

        return getEntity(entity);
    }

    public static <ENTITY> ENTITY getEntity(Optional<ENTITY> entity) {
        if (entity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return entity.get();
    }

    public static <ENTITY extends AbstractEntity> void throwExceptionIfDuplicate(ENTITY entity, Optional<ENTITY> foundEntity) {
        foundEntity.ifPresent(found -> {
            if (!found.getEntityId().equals(entity.getEntityId())) {
                throw new ViolatesConstraintException();
            }
        });
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

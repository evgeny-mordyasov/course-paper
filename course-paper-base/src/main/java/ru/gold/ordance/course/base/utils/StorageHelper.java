package ru.gold.ordance.course.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.gold.ordance.course.base.entity.*;
import ru.gold.ordance.course.base.exception.NotFoundException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Component
public class StorageHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageHelper.class);

    private final EntityManager manager;

    public StorageHelper(EntityManager manager) {
        this.manager = manager;
    }

    public <T> T findById(Class<T> clazz, Long id) {
        return manager.find(clazz, id);
    }

    public void validate(List<AbstractEntity> entities) {
        entities.forEach(this::validate);
    }

    public void validate(AbstractEntity entity) {
        if (entity.getId() != null) {
            existsById(entity, "entity");
        }

        existsInternalEntityById(entity);
    }

    private void existsInternalEntityById(AbstractEntity entity) {
        if (entity instanceof Client
                || entity instanceof Classification
                || entity instanceof Language) {
            return;
        }

        if (entity instanceof Document) {
            existsInternalEntity(((Document) entity).getClassification());
        } else if (entity instanceof LnkDocumentLanguage) {
            existsInternalEntity(((LnkDocumentLanguage) entity).getDocument());
            existsInternalEntity(((LnkDocumentLanguage) entity).getLanguage());
        } else {
            throw new IllegalArgumentException("The transmitted entity is not supported by the current method.");
        }
    }

    private void existsInternalEntity(AbstractEntity entity) {
        existsById(entity, "internal entity");
    }

    private void existsById(AbstractEntity entity, String entityName) {
        Optional<AbstractEntity> found = find(entity);

        if (found.isEmpty()) {
            LOGGER.info("The " + entityName + " by id not found. id = {}", entity.getId());
            throw new NotFoundException("The " + entityName + " by id not found.");
        }
    }

    private Optional<AbstractEntity> find(AbstractEntity entity) {
        return Optional.ofNullable(manager.find(entity.getClass(), entity.getId()));
    }
}

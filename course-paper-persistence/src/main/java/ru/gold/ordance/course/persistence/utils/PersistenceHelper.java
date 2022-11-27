package ru.gold.ordance.course.persistence.utils;

import ru.gold.ordance.course.common.exception.ViolatesConstraintException;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;

import java.util.Optional;

public final class PersistenceHelper {
    private PersistenceHelper() {
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <ENTITY extends AbstractEntity> void throwExceptionIfDuplicate(ENTITY entity, Optional<ENTITY> foundEntity) {
        foundEntity.ifPresent(found -> {
            if (!found.getEntityId().equals(entity.getEntityId())) {
                throw new ViolatesConstraintException();
            }
        });
    }
}
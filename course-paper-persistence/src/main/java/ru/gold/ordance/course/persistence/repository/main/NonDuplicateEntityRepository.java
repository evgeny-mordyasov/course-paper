package ru.gold.ordance.course.persistence.repository.main;

import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.common.exception.ViolatesConstraintException;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;

import java.util.Optional;
import java.util.function.Supplier;

@NoRepositoryBean
public interface NonDuplicateEntityRepository<ENTITY extends AbstractEntity> extends EntityRepository<ENTITY> {
    default ENTITY preserve(ENTITY newEntity, Supplier<Optional<ENTITY>> entityFromStorage) {
        checkingForDuplicate(newEntity, entityFromStorage);
        return defaultPreserve(newEntity);
    }

    default ENTITY update(ENTITY newEntity, Supplier<Optional<ENTITY>> entityFromStorage) {
        checkingForDuplicate(newEntity, entityFromStorage);
        return defaultUpdate(newEntity);
    }

    private void checkingForDuplicate(ENTITY newEntity, Supplier<Optional<ENTITY>> entityFromStorage) {
        entityFromStorage.get()
                .ifPresent(efs -> {
                    if (!efs.getEntityId().equals(newEntity.getEntityId())) {
                        throw new ViolatesConstraintException();
                    }
                });
    }
}

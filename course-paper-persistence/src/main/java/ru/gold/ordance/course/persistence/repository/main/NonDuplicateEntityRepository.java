package ru.gold.ordance.course.persistence.repository.main;

import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;

import java.util.Optional;
import java.util.function.Supplier;

import static ru.gold.ordance.course.persistence.utils.PersistenceHelper.throwExceptionIfDuplicate;

@NoRepositoryBean
public interface NonDuplicateEntityRepository<ENTITY extends AbstractEntity> extends EntityRepository<ENTITY> {
    default ENTITY preserve(ENTITY entity, Supplier<Optional<ENTITY>> entityFromStorage) {
        validate(entity, entityFromStorage);
        return defaultPreserve(entity);
    }

    default ENTITY update(ENTITY entity, Supplier<Optional<ENTITY>> entityFromStorage) {
        validate(entity, entityFromStorage);
        return defaultUpdate(entity);
    }

    private void validate(ENTITY entity, Supplier<Optional<ENTITY>> entityFromStorage) {
        throwExceptionIfDuplicate(entity, entityFromStorage.get());
    }
}

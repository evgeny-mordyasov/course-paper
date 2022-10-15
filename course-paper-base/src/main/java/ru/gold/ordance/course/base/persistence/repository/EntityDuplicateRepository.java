package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.base.entity.AbstractEntity;

import java.util.Optional;
import java.util.function.Supplier;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.throwExceptionIfDuplicate;

@NoRepositoryBean
public interface EntityDuplicateRepository<ENTITY extends AbstractEntity> extends EntityRepository<ENTITY> {

    default ENTITY preserve(ENTITY entity, Supplier<Optional<ENTITY>> entityFromStorage) {
        throwExceptionIfDuplicate(entity, entityFromStorage.get());
        return EntityRepository.super.preserve(entity);
    }

    default ENTITY update(ENTITY entity, Supplier<Optional<ENTITY>> entityFromStorage) {
        throwExceptionIfDuplicate(entity, entityFromStorage.get());
        return EntityRepository.super.update(entity);
    }
}

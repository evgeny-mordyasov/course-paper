package ru.gold.ordance.course.persistence.repository.main;

import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;
import ru.gold.ordance.course.persistence.entity.ContainingInternalEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<ENTITY extends AbstractEntity> extends RefreshedJpaRepository<ENTITY> {
    List<ENTITY> findAll();
    Optional<ENTITY> findByEntityId(Long entityId);

    default ENTITY defaultPreserve(ENTITY entity) {
        if (entity instanceof ContainingInternalEntity) {
            exists(((ContainingInternalEntity) entity).getInternalEntities());
        }

        ENTITY saved = saveAndFlush(entity);
        refresh(entity);

        return saved;
    }

    default ENTITY defaultUpdate(ENTITY entity) {
        if (findByEntityId(entity.getEntityId()).isEmpty()) {
            throw new EntityNotFoundException();
        }

        return saveAndFlush(entity);
    }

    default void deleteByEntityId(Long entityId) {
        Optional<ENTITY> entity = findByEntityId(entityId);

        if (entity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        actionForDeleting(entity.get());
    }

    default void actionForDeleting(ENTITY entity) {
        deleteById(entity.getEntityId());
    }
}
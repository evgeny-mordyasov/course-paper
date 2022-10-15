package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.base.entity.AbstractEntity;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getEntity;

@NoRepositoryBean
public interface EntityRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long> {
    Optional<ENTITY> findByEntityId(Long entityId);
    List<ENTITY> findAll();

    default ENTITY getByEntityId(Long entityId) {
        return getEntity(findByEntityId(entityId));
    }

    default ENTITY preserve(ENTITY entity) {
        return saveAndFlush(entity);
    }

    default ENTITY update(ENTITY entity) {
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
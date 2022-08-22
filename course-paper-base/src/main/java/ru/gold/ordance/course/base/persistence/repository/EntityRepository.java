package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.gold.ordance.course.base.entity.AbstractEntity;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long> {
    Optional<ENTITY> findByEntityId(Long entityId);
    List<ENTITY> findAll();

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
        if (findByEntityId(entityId).isEmpty()) {
            throw new EntityNotFoundException();
        }

        deleteById(entityId);
    }
}
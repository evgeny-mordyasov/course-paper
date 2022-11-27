package ru.gold.ordance.course.base.service.core;

import ru.gold.ordance.course.persistence.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface StandardEntityService<ENTITY extends AbstractEntity> {
    List<ENTITY> findAll();
    Optional<ENTITY> findByEntityId(Long entityId);
    ENTITY save(ENTITY entity);
    void deleteByEntityId(Long entityId);
}

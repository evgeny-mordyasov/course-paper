package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractService<ENTITY extends AbstractEntity> {
    List<ENTITY> findAll();
    Optional<ENTITY> findByEntityId(Long entityId);
    ENTITY save(ENTITY entity);
    ENTITY update(ENTITY entity);
    void deleteByEntityId(Long entityId);
}

package ru.gold.ordance.course.base.service.core;

import ru.gold.ordance.course.base.entity.AbstractEntity;

import java.util.List;

public interface StandardEntityService<ENTITY extends AbstractEntity> {
    List<ENTITY> findAll();
    ENTITY getByEntityId(Long entityId);
    ENTITY save(ENTITY entity);
    void deleteByEntityId(Long entityId);
}

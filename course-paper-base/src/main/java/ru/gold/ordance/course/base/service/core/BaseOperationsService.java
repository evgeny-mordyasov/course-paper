package ru.gold.ordance.course.base.service.core;

import ru.gold.ordance.course.base.entity.AbstractEntity;

public interface BaseOperationsService<ENTITY extends AbstractEntity> {
    ENTITY save(ENTITY entity);
    void deleteByEntityId(Long entityId);
}

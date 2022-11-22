package ru.gold.ordance.course.base.service.core;

import ru.gold.ordance.course.base.entity.AbstractEntity;

public interface UpdatableEntityService<ENTITY extends AbstractEntity> extends StandardEntityService<ENTITY> {
    ENTITY update(ENTITY entity);
}

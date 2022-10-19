package ru.gold.ordance.course.base.service.core;

import ru.gold.ordance.course.base.entity.AbstractEntity;

public interface NotUpdatableEntityService<ENTITY extends AbstractEntity> extends BaseSearchService<ENTITY>, BaseOperationsService<ENTITY> {
}

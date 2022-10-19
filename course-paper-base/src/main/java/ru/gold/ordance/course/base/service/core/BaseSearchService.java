package ru.gold.ordance.course.base.service.core;

import ru.gold.ordance.course.base.entity.AbstractEntity;

import java.util.List;

public interface BaseSearchService<ENTITY extends AbstractEntity> {
    List<ENTITY> findAll();
    ENTITY findByEntityId(Long entityId);
}
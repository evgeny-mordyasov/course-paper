package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface AbstractService<ENTITY extends AbstractEntity> {
    List<ENTITY> findAll();
    Optional<ENTITY> findById(Long id);
    ENTITY save(ENTITY entity);
    void update(ENTITY entity);
    void deleteById(Long id);
}

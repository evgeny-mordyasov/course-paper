package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.Classification;

import java.util.Optional;

public interface ClassificationService extends AbstractService<Classification> {
    Optional<Classification> findByName(String name);
}

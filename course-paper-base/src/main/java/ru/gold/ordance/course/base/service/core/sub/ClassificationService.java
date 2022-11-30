package ru.gold.ordance.course.base.service.core.sub;


import ru.gold.ordance.course.base.service.core.UpdatableEntityService;
import ru.gold.ordance.course.persistence.entity.impl.Classification;

import java.util.Optional;

public interface ClassificationService extends UpdatableEntityService<Classification> {
    Optional<Classification> findByName(String name);
}

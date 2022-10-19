package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.service.core.UpdatableEntityService;

public interface ClassificationService extends UpdatableEntityService<Classification> {
    Classification findByName(String name);
}

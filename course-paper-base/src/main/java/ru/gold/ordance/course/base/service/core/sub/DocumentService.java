package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.service.core.UpdatableEntityService;

import java.util.List;

public interface DocumentService extends UpdatableEntityService<Document> {
    List<Document> findByName(String name);
}

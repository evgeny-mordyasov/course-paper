package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.History;
import ru.gold.ordance.course.base.service.core.NotUpdatableEntityService;

import java.util.List;

public interface HistoryService extends NotUpdatableEntityService<History> {
    List<History> findByDocumentId(Long documentId);
}

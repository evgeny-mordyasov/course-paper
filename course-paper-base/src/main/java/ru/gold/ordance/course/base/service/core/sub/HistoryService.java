package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.service.core.NotUpdatableEntityService;
import ru.gold.ordance.course.persistence.entity.History;

import java.util.List;

public interface HistoryService extends NotUpdatableEntityService<History> {
    List<History> findByDocumentId(Long documentId);
}

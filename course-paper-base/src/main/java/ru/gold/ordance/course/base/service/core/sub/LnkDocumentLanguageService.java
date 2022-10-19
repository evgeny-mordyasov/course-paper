package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.core.NotUpdatableEntityService;

public interface LnkDocumentLanguageService extends NotUpdatableEntityService<LnkDocumentLanguage> {
    LnkDocumentLanguage findByUrn(String URN);
    Long getQuantityByDocumentId(Long documentId);
    void deleteByUrn(String URN);
}

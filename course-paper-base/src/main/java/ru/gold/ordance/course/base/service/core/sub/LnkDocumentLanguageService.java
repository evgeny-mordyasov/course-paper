package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.core.NotUpdatableEntityService;

import java.util.List;

public interface LnkDocumentLanguageService extends NotUpdatableEntityService<LnkDocumentLanguage> {
    List<LnkDocumentLanguage> findByDocumentId(Long documentId);
    LnkDocumentLanguage findByUrn(String URN);
    LnkDocumentLanguage findByDocumentIdAndLanguageId(Long documentId, Long languageId);
    Long getQuantityByDocumentId(Long documentId);
    void deleteByUrn(String URN);
}

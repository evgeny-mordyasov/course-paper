package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.core.NotUpdatableEntityService;

import java.util.List;
import java.util.Optional;

public interface LnkDocumentLanguageService extends NotUpdatableEntityService<LnkDocumentLanguage> {
    List<LnkDocumentLanguage> getByDocumentId(Long documentId);
    LnkDocumentLanguage getByUrn(String URN);
    LnkDocumentLanguage getByDocumentIdAndLanguageId(Long documentId, Long languageId);
    Optional<LnkDocumentLanguage> findByDocumentIdAndLanguageId(Long documentId, Long languageId);
    Long getQuantityByDocumentId(Long documentId);
    void deleteByUrn(String URN);
}

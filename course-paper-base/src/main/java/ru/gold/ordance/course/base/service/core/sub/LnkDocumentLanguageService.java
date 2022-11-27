package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.service.core.NotUpdatableEntityService;
import ru.gold.ordance.course.persistence.entity.LnkDocumentLanguage;

import java.util.List;
import java.util.Optional;

public interface LnkDocumentLanguageService extends NotUpdatableEntityService<LnkDocumentLanguage> {
    List<LnkDocumentLanguage> findByDocumentId(Long documentId);
    Optional<LnkDocumentLanguage> findByUrn(String URN);
    Optional<LnkDocumentLanguage> findByDocumentIdAndLanguageId(Long documentId, Long languageId);
    List<LnkDocumentLanguage> findByClassificationId(Long classificationId);
    Long getQuantityByDocumentId(Long documentId);
    void deleteByUrn(String URN);
}

package ru.gold.ordance.course.base.service;

import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;

import java.util.Optional;

public interface LnkDocumentLanguageService extends AbstractService<LnkDocumentLanguage> {
    LnkDocumentLanguage findByUrn(String URN);
    Long getQuantityByDocumentId(Long documentId);
    void deleteByUrn(String URN);
}

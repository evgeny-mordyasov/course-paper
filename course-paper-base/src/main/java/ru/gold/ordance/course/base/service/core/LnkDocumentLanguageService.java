package ru.gold.ordance.course.base.service.core;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.impl.LnkDocumentLanguage;
import ru.gold.ordance.course.persistence.repository.sub.LnkDocumentLanguageRepository;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class LnkDocumentLanguageService {
    private final LnkDocumentLanguageRepository repository;

    public LnkDocumentLanguageService(LnkDocumentLanguageRepository repository) {
        this.repository = repository;
    }

    public List<LnkDocumentLanguage> findAll() {
        return repository.findAll();
    }

    public List<LnkDocumentLanguage> findByDocumentId(Long documentId) {
        return repository.findByDocument_EntityId(documentId);
    }

    public Optional<LnkDocumentLanguage> findByUrn(String urn) {
        return repository.findByUrn(urn);
    }

    public Optional<LnkDocumentLanguage> findByDocumentIdAndLanguageId(Long documentId, Long languageId) {
        return repository.findByDocument_EntityIdAndLanguage_EntityId(documentId, languageId);
    }

    public List<LnkDocumentLanguage> findByClassificationId(Long classificationId) {
        return repository.findByDocument_Classification_EntityId(classificationId);
    }

    public Long getQuantityByDocumentId(Long documentId) {
        return repository.countLnkDocumentLanguagesByDocument_EntityId(documentId);
    }

    public LnkDocumentLanguage save(LnkDocumentLanguage obj) {
        return repository.preserve(obj);
    }

    public void deleteByUrn(String urn) {
        repository.deleteByUrn(urn);
    }
}

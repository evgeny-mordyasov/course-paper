package ru.gold.ordance.course.base.service.core.sub.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.persistence.repository.sub.LnkDocumentLanguageRepository;
import ru.gold.ordance.course.base.service.core.sub.LnkDocumentLanguageService;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class LnkDocumentLanguageServiceImpl implements LnkDocumentLanguageService {
    private final LnkDocumentLanguageRepository repository;

    public LnkDocumentLanguageServiceImpl(LnkDocumentLanguageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LnkDocumentLanguage> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<LnkDocumentLanguage> findByEntityId(@NotNull Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public List<LnkDocumentLanguage> findByDocumentId(Long documentId) {
        return repository.findByDocument_EntityId(documentId);
    }

    @Override
    public Optional<LnkDocumentLanguage> findByUrn(@NotNull String URN) {
        return repository.findByUrn(URN);
    }

    @Override
    public Optional<LnkDocumentLanguage> findByDocumentIdAndLanguageId(Long documentId, Long languageId) {
        return repository.findByDocument_EntityIdAndLanguage_EntityId(documentId, languageId);
    }

    @Override
    public List<LnkDocumentLanguage> findByClassificationId(Long classificationId) {
        return repository.findByDocument_Classification_EntityId(classificationId);
    }

    @Override
    public Long getQuantityByDocumentId(Long documentId) {
        return repository.countLnkDocumentLanguagesByDocument_EntityId(documentId);
    }

    @Override
    public LnkDocumentLanguage save(@NotNull LnkDocumentLanguage lnk) {
        return repository.preserve(lnk);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }

    @Override
    public void deleteByUrn(@NotNull String URN) {
        repository.deleteByUrn(URN);
    }
}

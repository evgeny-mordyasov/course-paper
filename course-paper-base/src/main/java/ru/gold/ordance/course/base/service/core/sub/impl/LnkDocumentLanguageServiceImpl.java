package ru.gold.ordance.course.base.service.core.sub.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.persistence.repository.LnkDocumentLanguageRepository;
import ru.gold.ordance.course.base.service.core.sub.LnkDocumentLanguageService;

import java.util.List;

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
    public LnkDocumentLanguage findByEntityId(@NotNull Long entityId) {
        return repository.getByEntityId(entityId);
    }

    @Override
    public LnkDocumentLanguage findByUrn(@NotNull String URN) {
        return repository.getLnkDocumentLanguageByUrn(URN);
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

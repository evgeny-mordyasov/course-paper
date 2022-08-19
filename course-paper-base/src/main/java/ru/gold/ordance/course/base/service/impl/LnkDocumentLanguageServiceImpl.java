package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.repository.LnkDocumentLanguageRepository;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;

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
        return repository.findById(entityId);
    }

    @Override
    public Optional<LnkDocumentLanguage> findByUrn(@NotNull String URN) {
        return repository.findLnkDocumentLanguageByUrn(URN);
    }

    @Override
    public Long findQuantityByDocumentId(Long documentId) {
        return repository.countLnkDocumentLanguagesByDocument_EntityId(documentId);
    }

    @Override
    public LnkDocumentLanguage save(@NotNull LnkDocumentLanguage lnk) {
        return repository.saveAndFlush(lnk);
    }

    @Override
    public LnkDocumentLanguage update(@NotNull LnkDocumentLanguage lnk) {
        return repository.saveAndFlush(lnk);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
        LnkDocumentLanguage lnk = repository.findById(entityId)
                .orElseThrow(NotFoundException::new);

        repository.deleteById(lnk.getEntityId());
    }

    @Override
    public void deleteByUrn(@NotNull String URN) {
        LnkDocumentLanguage lnk = repository.findLnkDocumentLanguageByUrn(URN)
                .orElseThrow(NotFoundException::new);

        repository.deleteById(lnk.getEntityId());
    }
}

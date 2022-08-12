package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.LnkDocumentLanguageRepository;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;

import java.util.List;
import java.util.Optional;

@Service
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
    public Optional<LnkDocumentLanguage> findById(@NotNull Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<LnkDocumentLanguage> findByUrn(@NotNull String URN) {
        return repository.findLnkDocumentLanguageByUrn(URN);
    }

    @Override
    public Long findQuantityByDocumentId(Long documentId) {
        return repository.countLnkDocumentLanguagesByDocument_Id(documentId);
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
    public void deleteById(Long id) {
        LnkDocumentLanguage lnk = repository.findById(id)
                .orElseThrow(NotFoundException::new);

        repository.deleteById(lnk.getId());
    }

    @Override
    public void deleteByUrn(@NotNull String URN) {
        LnkDocumentLanguage lnk = repository.findLnkDocumentLanguageByUrn(URN)
                .orElseThrow(NotFoundException::new);

        repository.deleteById(lnk.getId());
    }
}

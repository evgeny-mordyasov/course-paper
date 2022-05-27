package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.persistence.LnkDocumentLanguageRepository;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class LnkDocumentLanguageServiceImpl implements LnkDocumentLanguageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LnkDocumentLanguageServiceImpl.class);

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
    public LnkDocumentLanguage save(@NotNull LnkDocumentLanguage lnk) {
        return repository.saveAndFlush(lnk);
    }

    @Override
    public void update(@NotNull LnkDocumentLanguage lnk) {
        repository.saveAndFlush(lnk);
    }

    @Override
    public void deleteById(Long id) {
        Optional<LnkDocumentLanguage> found = repository.findById(id);
        found.ifPresent(lnk -> repository.deleteById(lnk.getId()));
    }
}

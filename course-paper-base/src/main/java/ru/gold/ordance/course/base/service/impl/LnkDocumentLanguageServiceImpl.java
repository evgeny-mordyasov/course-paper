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
        LOGGER.info("The search for all lnk-dl has started.");

        List<LnkDocumentLanguage> documents = repository.findAll();

        LOGGER.info("Size of list: {}", documents.size());

        return documents;
    }

    @Override
    public Optional<LnkDocumentLanguage> findById(@NotNull Long id) {
        LOGGER.info("The search by id lnk-dl has started.");

        Optional<LnkDocumentLanguage> lnk = repository.findById(id);

        LOGGER.info("The lnk-dl {}",
                (lnk.isEmpty() ? "not found. entityId = " + id : "was found. classification = " + lnk.get()));

        return lnk;
    }

    @Override
    public LnkDocumentLanguage save(@NotNull LnkDocumentLanguage lnk) {
        LOGGER.info("The save lnk-dl has started.");

        LnkDocumentLanguage saved = repository.saveAndFlush(lnk);

        LOGGER.info("The save lnk has finished.");

        return saved;
    }

    @Override
    public void update(@NotNull LnkDocumentLanguage lnk) {
        LOGGER.info("The update lnk-dl has started.");

        repository.saveAndFlush(lnk);

        LOGGER.info("The update lnk-dl has finished.");
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("The delete lnk-dl has started.");

        Optional<LnkDocumentLanguage> found = repository.findById(id);
        found.ifPresent(lnk -> repository.deleteById(lnk.getId()));

        LOGGER.info("The lnk-dl " + (found.isPresent() ? "was deleted" : "by id does not exist") + ". entityId = {}", id);
    }
}

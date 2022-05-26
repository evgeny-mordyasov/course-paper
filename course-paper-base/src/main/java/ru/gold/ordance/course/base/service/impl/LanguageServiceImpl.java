package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.persistence.LanguageRepository;
import ru.gold.ordance.course.base.service.LanguageService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class LanguageServiceImpl implements LanguageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private final LanguageRepository repository;

    public LanguageServiceImpl(LanguageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Language> findAll() {
        LOGGER.info("The search for all language has started.");

        List<Language> languages = repository.findAll();

        LOGGER.info("Size of list: {}", languages.size());

        return languages;
    }

    @Override
    public Optional<Language> findById(@NotNull Long id) {
        LOGGER.info("The search by id language has started.");

        Optional<Language> language = repository.findById(id);

        LOGGER.info("The language {}",
                (language.isEmpty() ? "not found. entityId = " + id : "was found. language = " + language.get()));

        return language;
    }

    @Override
    public Optional<Language> findByName(@NotNull String name) {
        LOGGER.info("The search by name language has started.");

        Optional<Language> language = repository.findByName(name);

        LOGGER.info("The language {}",
                (language.isEmpty() ? "not found. name = " + name : "was found. language = " + language.get()));

        return language;
    }

    @Override
    public Language save(@NotNull Language language) {
        LOGGER.info("The save language has started.");

        Language saved = repository.saveAndFlush(language);

        LOGGER.info("The save language has finished.");

        return saved;
    }

    @Override
    public void update(@NotNull Language language) {
        LOGGER.info("The update language has started.");

        repository.saveAndFlush(language);

        LOGGER.info("The update language has finished.");
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("The delete language has started.");

        Optional<Language> found = repository.findById(id);
        found.ifPresent(l -> repository.deleteById(l.getId()));

        LOGGER.info("The language " + (found.isPresent() ? "was deleted" : "by id does not exist") + ". entityId = {}", id);
    }
}

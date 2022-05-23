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

        if (language.isEmpty()) {
            LOGGER.info("The language not found. entityId = {}", id);
        } else {
            LOGGER.info("The language was found. language = {}", language.get());
        }

        return language;
    }

    @Override
    public Optional<Language> findByName(@NotNull String name) {
        LOGGER.info("The search by name language has started.");

        Optional<Language> language = repository.findByName(name);

        if (language.isEmpty()) {
            LOGGER.info("The language not found. name = {}", name);
        } else {
            LOGGER.info("The language was found. language = {}", language.get());
        }

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

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The language was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The language by id does not exist. entityId = {}", id);
        }
    }
}

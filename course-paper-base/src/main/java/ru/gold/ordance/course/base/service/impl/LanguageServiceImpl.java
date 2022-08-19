package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.repository.LanguageRepository;
import ru.gold.ordance.course.base.service.LanguageService;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository repository;

    public LanguageServiceImpl(LanguageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Language> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Language> findByEntityId(@NotNull Long entityId) {
        return repository.findById(entityId);
    }

    @Override
    public Optional<Language> findByName(@NotNull String name) {
        return repository.findByName(name);
    }

    @Override
    public Language save(@NotNull Language language) {
        return repository.saveAndFlush(language);
    }

    @Override
    public Language update(@NotNull Language language) {
        return repository.saveAndFlush(language);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
       Language language = repository.findById(entityId)
               .orElseThrow(NotFoundException::new);

       repository.deleteById(language.getEntityId());
    }
}

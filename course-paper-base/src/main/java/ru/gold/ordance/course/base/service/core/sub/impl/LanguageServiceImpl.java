package ru.gold.ordance.course.base.service.core.sub.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.Language;
import ru.gold.ordance.course.persistence.repository.sub.LanguageRepository;
import ru.gold.ordance.course.base.service.core.sub.LanguageService;

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
        return repository.findByEntityId(entityId);
    }

    @Override
    public Optional<Language> findByName(@NotNull String name) {
        return repository.findByName(name);
    }

    @Override
    public Language save(@NotNull Language language) {
        return repository.preserve(language);
    }

    @Override
    public Language update(@NotNull Language language) {
        return repository.update(language);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
       repository.deleteByEntityId(entityId);
    }
}

package ru.gold.ordance.course.base.service.core.sub.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.persistence.repository.LanguageRepository;
import ru.gold.ordance.course.base.service.core.sub.LanguageService;

import java.util.List;

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
    public Language getByEntityId(@NotNull Long entityId) {
        return repository.getByEntityId(entityId);
    }

    @Override
    public Language getByName(@NotNull String name) {
        return repository.getByName(name);
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

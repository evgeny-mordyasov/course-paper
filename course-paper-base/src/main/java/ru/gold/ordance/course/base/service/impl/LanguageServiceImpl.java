package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.LanguageRepository;
import ru.gold.ordance.course.base.service.LanguageService;

import java.util.List;
import java.util.Optional;

@Service
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
    public Optional<Language> findById(@NotNull Long id) {
        return repository.findById(id);
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
    public void deleteById(Long id) {
       Language language = repository.findById(id)
               .orElseThrow(NotFoundException::new);

       repository.deleteById(language.getId());
    }
}

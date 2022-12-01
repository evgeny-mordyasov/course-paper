package ru.gold.ordance.course.base.service.core;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.persistence.repository.sub.LanguageRepository;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class LanguageService {
    private final LanguageRepository repository;

    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    public List<Language> findAll() {
        return repository.findAll();
    }

    public Optional<Language> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    public Optional<Language> findByName(String name) {
        return repository.findByName(name);
    }

    public Language save(Language obj) {
        return repository.preserve(obj);
    }

    public Language update(Language obj) {
        return repository.update(obj);
    }

    public void deleteByEntityId(Long entityId) {
       repository.deleteByEntityId(entityId);
    }
}

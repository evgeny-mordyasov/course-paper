package ru.gold.ordance.course.base.service.core;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.impl.Classification;
import ru.gold.ordance.course.persistence.repository.sub.ClassificationRepository;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClassificationService {
    private final ClassificationRepository repository;

    public ClassificationService(ClassificationRepository repository) {
        this.repository = repository;
    }

    public List<Classification> findAll() {
        return repository.findAll();
    }

    public Optional<Classification> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    public Optional<Classification> findByName(String name) {
        return repository.findByName(name);
    }

    public Classification save(Classification obj) {
        return repository.preserve(obj);
    }

    public Classification update(Classification obj) {
        return repository.update(obj);
    }

    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}

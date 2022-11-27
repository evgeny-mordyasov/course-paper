package ru.gold.ordance.course.base.service.core.sub.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.service.core.sub.ClassificationService;
import ru.gold.ordance.course.persistence.entity.Classification;
import ru.gold.ordance.course.persistence.repository.sub.ClassificationRepository;

import java.util.List;
import java.util.Optional;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClassificationServiceImpl implements ClassificationService {
    private final ClassificationRepository repository;

    public ClassificationServiceImpl(ClassificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Classification> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Classification> findByEntityId(@NotNull Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public Optional<Classification> findByName(@NotNull String name) {
        return repository.findByName(name);
    }

    @Override
    public Classification save(@NotNull Classification classification) {
        return repository.preserve(classification);
    }

    @Override
    public Classification update(@NotNull Classification classification) {
        return repository.update(classification);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}

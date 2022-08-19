package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.repository.ClassificationRepository;
import ru.gold.ordance.course.base.service.ClassificationService;

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
        return repository.findById(entityId);
    }

    @Override
    public Optional<Classification> findByName(@NotNull String name) {
        return repository.findByName(name);
    }

    @Override
    public Classification save(@NotNull Classification classification) {
        return repository.saveAndFlush(classification);
    }

    @Override
    public Classification update(@NotNull Classification classification) {
        return repository.saveAndFlush(classification);
    }

    @Override
    public void deleteByEntityId(Long entityId) {
        Classification classification = repository.findById(entityId)
                .orElseThrow(NotFoundException::new);

        repository.deleteById(classification.getEntityId());
    }
}

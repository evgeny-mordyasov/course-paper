package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.persistence.ClassificationRepository;
import ru.gold.ordance.course.base.service.ClassificationService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClassificationServiceImpl implements ClassificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationServiceImpl.class);

    private final ClassificationRepository repository;

    public ClassificationServiceImpl(ClassificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Classification> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Classification> findById(@NotNull Long id) {
        return repository.findById(id);
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
    public void update(@NotNull Classification classification) {
        repository.saveAndFlush(classification);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Classification> found = repository.findById(id);
        found.ifPresent(c -> repository.deleteById(c.getId()));
    }
}

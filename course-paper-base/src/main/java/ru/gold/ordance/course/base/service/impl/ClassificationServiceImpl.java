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
        LOGGER.info("The search for all classification has started.");

        List<Classification> classifications = repository.findAll();

        LOGGER.info("Size of list: {}", classifications.size());

        return classifications;
    }

    @Override
    public Optional<Classification> findById(@NotNull Long id) {
        LOGGER.info("The search by id classification has started.");

        Optional<Classification> classification = repository.findById(id);

        LOGGER.info("The classification {}",
                (classification.isEmpty() ? "not found. entityId = " + id : "was found. classification = " + classification.get()));

        return classification;
    }

    @Override
    public Optional<Classification> findByName(@NotNull String name) {
        LOGGER.info("The search by name classification has started.");

        Optional<Classification> classification = repository.findByName(name);

        LOGGER.info("The classification {}",
                (classification.isEmpty() ? "not found. name = " + name : "was found. classification = " + classification.get()));

        return classification;
    }

    @Override
    public Classification save(@NotNull Classification classification) {
        LOGGER.info("The save classification has started.");

        Classification saved = repository.saveAndFlush(classification);

        LOGGER.info("The save classification has finished.");

        return saved;
    }

    @Override
    public void update(@NotNull Classification classification) {
        LOGGER.info("The update classification has started.");

        repository.saveAndFlush(classification);

        LOGGER.info("The update classification has finished.");
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("The delete classification has started.");

        Optional<Classification> found = repository.findById(id);
        found.ifPresent(c -> repository.deleteById(c.getId()));

        LOGGER.info("The classification " + (found.isPresent() ? "was deleted" : "by id does not exist") + ". entityId = {}", id);
    }
}

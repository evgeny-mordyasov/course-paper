package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.exception.NotFoundException;
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

        if (classification.isEmpty()) {
            LOGGER.info("The classification not found. entityId = {}", id);
        } else {
            LOGGER.info("The classification was found. classification = {}", classification.get());
        }

        return classification;
    }

    @Override
    public Optional<Classification> findByName(@NotNull String name) {
        LOGGER.info("The search by email classification has started.");

        Optional<Classification> classification = repository.findByName(name);

        if (classification.isEmpty()) {
            LOGGER.info("The classification not found. name = {}", name);
        } else {
            LOGGER.info("The classification was found. classification = {}", classification.get());
        }

        return classification;
    }

    @Override
    public void save(@NotNull Classification classification) {
        LOGGER.info("The save classification has started.");

        repository.saveAndFlush(classification);

        LOGGER.info("The save classification has finished.");
    }

    @Override
    public void update(@NotNull Classification classification) {
        LOGGER.info("The update classification has started.");

        Optional<Classification> found = repository.findById(classification.getId());
        if (found.isEmpty()) {
            LOGGER.info("The classification by id not found. id = {}", classification.getId());
            throw new NotFoundException("The classification by id not found.");
        }

        repository.saveAndFlush(classification);

        LOGGER.info("The update classification has finished.");
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("The delete classification has started.");

        Optional<Classification> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The classification was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The classification by id does not exist. entityId = {}", id);
        }
    }
}

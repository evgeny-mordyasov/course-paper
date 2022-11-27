package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gold.ordance.course.base.TestConfiguration;
import ru.gold.ordance.course.persistence.entity.Classification;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.repository.sub.ClassificationRepository;
import ru.gold.ordance.course.base.service.core.sub.ClassificationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createClassification;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = TestConfiguration.class)
public class ClassificationServiceTest {
    @Autowired
    private ClassificationService service;

    @Autowired
    private ClassificationRepository repository;

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Classification> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.preserve(createClassification());

        List<Classification> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.preserve(createClassification());
        repository.preserve(createClassification());

        List<Classification> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<Classification> classification = service.findByEntityId(fakeId);

        assertTrue(classification.isEmpty());
    }

    @Test
    public void findById_found() {
        Classification saved = repository.preserve(createClassification());

        Optional<Classification> classification = service.findByEntityId(saved.getEntityId());

        assertTrue(classification.isPresent());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = randomString();

        Optional<Classification> classification = service.findByName(fakeName);

        assertTrue(classification.isEmpty());
    }

    @Test
    public void findByName_found() {
        Classification saved = repository.preserve(createClassification());

        Optional<Classification> classification = service.findByName(saved.getName());

        assertTrue(classification.isPresent());
    }

    @Test
    public void save() {
        Classification saved = service.save(createClassification());

        Optional<Classification> found = repository.findById(saved.getEntityId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
    }

    @Test
    public void save_nameAlreadyExists() {
    }

    @Test
    public void update() {
        Classification saved = createClassification();
        Long entityId = repository.preserve(saved).getEntityId();
        Classification newObj = createClassification(entityId);

        Classification updatedClassification = service.update(newObj);

        assertEquals(newObj.getEntityId(), updatedClassification.getEntityId());
        assertEquals(newObj.getName(), updatedClassification.getName());
    }

    @Test
    public void deleteById_notFound() {
        long fakeId = generateId();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByEntityId(fakeId));
    }

    @Test
    public void deleteById_classificationExists() {
        Long entityId = repository.preserve(createClassification()).getEntityId();

        service.deleteByEntityId(entityId);

        Optional<Classification> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }
}

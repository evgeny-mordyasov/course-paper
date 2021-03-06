package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.persistence.ClassificationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createClassification;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
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
        repository.saveAndFlush(createClassification());

        List<Classification> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.saveAndFlush(createClassification());
        repository.saveAndFlush(createClassification());

        List<Classification> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<Classification> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Classification saved = repository.saveAndFlush(createClassification());

        Optional<Classification> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = randomString();

        Optional<Classification> found = service.findByName(fakeName);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_found() {
        Classification saved = repository.saveAndFlush(createClassification());

        Optional<Classification> found = service.findByName(saved.getName());

        assertTrue(found.isPresent());
    }

    @Test
    public void save() {
        Classification saved = service.save(createClassification());

        Optional<Classification> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
    }

    @Test
    public void save_nameAlreadyExists() {
        final String name = randomString();

        repository.saveAndFlush(createClassification(name));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(createClassification(name)));
    }

    @Test
    public void update() {
        Classification saved = createClassification();
        Long entityId = repository.saveAndFlush(saved).getId();
        Classification newObj = createClassification(entityId);

        service.update(newObj);
        Optional<Classification> found = repository.findById(entityId);

        assertTrue(found.isPresent());
        assertEquals(newObj.getId(), found.get().getId());
        assertEquals(newObj.getName(), found.get().getName());
    }

    @Test
    public void deleteById_classificationExists() {
        Long entityId = repository.saveAndFlush(createClassification()).getId();

        service.deleteById(entityId);

        Optional<Classification> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }
}

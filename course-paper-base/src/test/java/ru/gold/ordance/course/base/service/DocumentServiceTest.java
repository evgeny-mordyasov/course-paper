package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.persistence.ClassificationRepository;
import ru.gold.ordance.course.base.persistence.DocumentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createClassification;
import static ru.gold.ordance.course.base.EntityGenerator.createDocument;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
public class DocumentServiceTest {
    @Autowired
    private DocumentService service;

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private ClassificationRepository classificationRepository;

    private Classification classification;

    @BeforeEach
    public void setUp() {
        classification = classificationRepository.saveAndFlush(createClassification());
    }

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Document> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.saveAndFlush(createDocument(classification));

        List<Document> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.saveAndFlush(createDocument(classification));
        repository.saveAndFlush(createDocument(classification));

        List<Document> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<Document> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Document saved = repository.saveAndFlush(createDocument(classification));

        Optional<Document> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void findByName_noOneHasBeenFound() {
        String fakeName = randomString();
        int noOneHasBeenFound = 0;

        List<Document> found = service.findByName(fakeName);

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findByName_foundOne() {
        int foundOne = 1;

        Document saved = repository.saveAndFlush(createDocument(classification));

        List<Document> found = service.findByName(saved.getName());

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findByName_foundALot() {
        String name = randomString();
        int foundALot = 2;

        repository.saveAndFlush(createDocument(classification, name));
        repository.saveAndFlush(createDocument(classification, name));

        List<Document> found = service.findByName(name);

        assertEquals(foundALot, found.size());
    }

    @Test
    public void save() {
        Document saved = service.save(createDocument(classification));

        Optional<Document> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
        assertEquals(saved.getClassification(), found.get().getClassification());
    }

    @Test
    public void update() {
        Document saved = repository.saveAndFlush(createDocument(classification));
        Document newObj = createDocument(classification, saved.getId());

        service.update(newObj);
        Optional<Document> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(newObj.getId(), found.get().getId());
        assertEquals(newObj.getName(), found.get().getName());
        assertEquals(newObj.getClassification(), found.get().getClassification());
    }

    @Test
    public void deleteById_documentExists() {
        Long entityId = repository.saveAndFlush(createDocument(classification)).getId();

        service.deleteById(entityId);

        Optional<Document> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }
}

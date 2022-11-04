package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gold.ordance.course.base.TestConfiguration;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.persistence.repository.ClassificationRepository;
import ru.gold.ordance.course.base.persistence.repository.DocumentRepository;
import ru.gold.ordance.course.base.service.core.sub.DocumentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createClassification;
import static ru.gold.ordance.course.base.EntityGenerator.createDocument;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = TestConfiguration.class)
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
        repository.preserve(createDocument(classification));

        List<Document> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.preserve(createDocument(classification));
        repository.preserve(createDocument(classification));

        List<Document> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByEntityId(fakeId));
    }

    @Test
    public void findById_found() {
        Document saved = repository.preserve(createDocument(classification));

        assertDoesNotThrow(() -> service.getByEntityId(saved.getEntityId()));
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

        Document saved = repository.preserve(createDocument(classification));

        List<Document> found = service.findByName(saved.getName());

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findByName_foundALot() {
        String name = randomString();
        int foundALot = 2;

        repository.preserve(createDocument(classification, name));
        repository.preserve(createDocument(classification, name));

        List<Document> found = service.findByName(name);

        assertEquals(foundALot, found.size());
    }

    @Test
    public void save() {
        Document saved = service.save(createDocument(classification));

        Optional<Document> found = repository.findById(saved.getEntityId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
        assertEquals(saved.getClassification(), found.get().getClassification());
    }

    @Test
    public void update() {
        Document saved = repository.preserve(createDocument(classification));
        Document newObj = createDocument(classification, saved.getEntityId());

        Document updatedDocument = service.update(newObj);

        assertEquals(newObj.getEntityId(), updatedDocument.getEntityId());
        assertEquals(newObj.getName(), updatedDocument.getName());
        assertEquals(newObj.getClassification(), updatedDocument.getClassification());
    }

    @Test
    public void deleteById_notFound() {
        long fakeId = generateId();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByEntityId(fakeId));
    }

    @Test
    public void deleteById_documentExists() {
        Long entityId = repository.preserve(createDocument(classification)).getEntityId();

        service.deleteByEntityId(entityId);

        Optional<Document> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }
}

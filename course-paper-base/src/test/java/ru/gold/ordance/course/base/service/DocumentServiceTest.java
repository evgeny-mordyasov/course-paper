package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gold.ordance.course.base.TestConfiguration;
import ru.gold.ordance.course.persistence.entity.impl.Classification;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.repository.sub.ClassificationRepository;
import ru.gold.ordance.course.persistence.repository.sub.DocumentRepository;
import ru.gold.ordance.course.base.service.core.sub.DocumentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.*;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;

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

        Optional<Document> document = service.findByEntityId(fakeId);

        assertTrue(document.isEmpty());
    }

    @Test
    public void findById_found() {
        Document saved = repository.preserve(createDocument(classification));

        Optional<Document> document = service.findByEntityId(saved.getEntityId());

        assertTrue(document.isPresent());
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

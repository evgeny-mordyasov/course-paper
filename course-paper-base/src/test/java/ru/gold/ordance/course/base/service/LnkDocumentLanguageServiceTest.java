package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.persistence.ClassificationRepository;
import ru.gold.ordance.course.base.persistence.DocumentRepository;
import ru.gold.ordance.course.base.persistence.LanguageRepository;
import ru.gold.ordance.course.base.persistence.LnkDocumentLanguageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.*;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
public class LnkDocumentLanguageServiceTest {
    @Autowired
    private LnkDocumentLanguageService service;

    @Autowired
    private LnkDocumentLanguageRepository repository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private LanguageRepository languageRepository;

    private Document document;

    private Language language;

    @BeforeEach
    public void setUp() {
        Classification classification = classificationRepository.save(createClassification());
        document = documentRepository.save(createDocument(classification));

        language = languageRepository.save(createLanguage());
    }

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<LnkDocumentLanguage> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.save(createLnk(document, language));

        List<LnkDocumentLanguage> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.save(createLnk(document, language));
        repository.save(createLnk(document, language));

        List<LnkDocumentLanguage> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<LnkDocumentLanguage> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        LnkDocumentLanguage saved = repository.save(createLnk(document, language));

        Optional<LnkDocumentLanguage> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void save() {
        LnkDocumentLanguage saved = repository.save(createLnk(document, language));

        Optional<LnkDocumentLanguage> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getDocument(), found.get().getDocument());
        assertEquals(saved.getLanguage(), found.get().getLanguage());
        assertEquals(saved.getUrn(), found.get().getUrn());
    }

    @Test
    public void save_urnAlreadyExists() {
        final String urn = randomString();

        repository.save(createLnk(document, language, urn));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(createLnk(document, language, urn)));
    }

    @Test
    public void update() {
        LnkDocumentLanguage saved = createLnk(document, language);
        Long entityId = repository.save(saved).getId();
        LnkDocumentLanguage newObj = createLnk(document, language, entityId);

        service.update(newObj);
        Optional<LnkDocumentLanguage> found = repository.findById(entityId);

        assertTrue(found.isPresent());
        assertEquals(newObj.getId(), found.get().getId());
        assertEquals(newObj.getDocument(), found.get().getDocument());
        assertEquals(newObj.getLanguage(), found.get().getLanguage());
        assertEquals(newObj.getUrn(), found.get().getUrn());
    }

    @Test
    public void deleteById_classificationExists() {
        Long entityId = repository.save(createLnk(document, language)).getId();

        service.deleteById(entityId);

        Optional<LnkDocumentLanguage> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }
}

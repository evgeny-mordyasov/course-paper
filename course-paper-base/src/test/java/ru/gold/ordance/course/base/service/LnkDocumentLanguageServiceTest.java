package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gold.ordance.course.base.TestConfiguration;
import ru.gold.ordance.course.persistence.entity.impl.Classification;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.persistence.entity.impl.LnkDocumentLanguage;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.repository.sub.ClassificationRepository;
import ru.gold.ordance.course.persistence.repository.sub.DocumentRepository;
import ru.gold.ordance.course.persistence.repository.sub.LanguageRepository;
import ru.gold.ordance.course.persistence.repository.sub.LnkDocumentLanguageRepository;
import ru.gold.ordance.course.base.service.core.sub.LnkDocumentLanguageService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.*;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = TestConfiguration.class)
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
        Classification classification = classificationRepository.saveAndFlush(createClassification());
        document = documentRepository.saveAndFlush(createDocument(classification));

        language = languageRepository.saveAndFlush(createLanguage());
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
        repository.preserve(createLnk(document, language));

        List<LnkDocumentLanguage> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.preserve(createLnk(document, language));
        repository.preserve(createLnk(document, language));

        List<LnkDocumentLanguage> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<LnkDocumentLanguage> lnkDocumentLanguage = service.findByEntityId(fakeId);

        assertTrue(lnkDocumentLanguage.isEmpty());
    }

    @Test
    public void findById_found() {
        LnkDocumentLanguage saved = repository.preserve(createLnk(document, language));

        Optional<LnkDocumentLanguage> lnkDocumentLanguage = service.findByEntityId(saved.getEntityId());

        assertTrue(lnkDocumentLanguage.isPresent());
    }

    @Test
    public void findByUrn_notFound() {
        String fakeURN = randomString();

        Optional<LnkDocumentLanguage> lnkDocumentLanguage = service.findByUrn(fakeURN);

        assertTrue(lnkDocumentLanguage.isEmpty());
    }

    @Test
    public void findByUrn_found() {
        final String URN = randomString();

        LnkDocumentLanguage saved = repository.preserve(createLnk(document, language, URN));

        Optional<LnkDocumentLanguage> lnkDocumentLanguage = service.findByUrn(saved.getUrn());

        assertTrue(lnkDocumentLanguage.isPresent());
    }

    @Test
    public void findQuantityByDocumentId_noOneHasBeenFound() {
        Long noOneHasBeenFound = 0L;
        Long fakeDocumentId = 999L;

        Long quantity = service.getQuantityByDocumentId(fakeDocumentId);

        assertEquals(noOneHasBeenFound, quantity);
    }

    @Test
    public void findQuantityByDocumentId_foundOne() {
        Long foundOne = 1L;

        repository.preserve(createLnk(document, language));
        Long quantity = service.getQuantityByDocumentId(document.getEntityId());

        assertEquals(foundOne, quantity);
    }

    @Test
    public void findQuantityByDocumentId_foundALot() {
        Long foundALot = 2L;

        repository.preserve(createLnk(document, language));
        repository.preserve(createLnk(document, language));
        Long quantity = service.getQuantityByDocumentId(document.getEntityId());

        assertEquals(foundALot, quantity);
    }


    @Test
    public void save() {
        LnkDocumentLanguage saved = repository.preserve(createLnk(document, language));

        Optional<LnkDocumentLanguage> found = repository.findById(saved.getEntityId());

        assertTrue(found.isPresent());
        assertEquals(saved.getDocument(), found.get().getDocument());
        assertEquals(saved.getLanguage(), found.get().getLanguage());
        assertEquals(saved.getUrn(), found.get().getUrn());
    }

    @Test
    public void save_urnAlreadyExists() {
    }

    @Test
    public void deleteById_notFound() {
        long fakeId = generateId();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByEntityId(fakeId));
    }

    @Test
    public void deleteById_lnkExists() {
        Long entityId = repository.preserve(createLnk(document, language)).getEntityId();

        service.deleteByEntityId(entityId);

        Optional<LnkDocumentLanguage> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }

    @Test
    public void deleteByUrn_notFound() {
        String fakeUrn = randomString();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByUrn(fakeUrn));
    }

    @Test
    public void deleteByUrn_lnkExists() {
        final String URN = randomString();

        repository.preserve(createLnk(document, language, URN));
        service.deleteByUrn(URN);

        Optional<LnkDocumentLanguage> found = repository.findByUrn(URN);

        assertFalse(found.isPresent());
    }
}

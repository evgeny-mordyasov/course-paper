package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gold.ordance.course.base.TestConfiguration;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.persistence.repository.LanguageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createLanguage;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = TestConfiguration.class)
public class LanguageServiceTest {
    @Autowired
    private LanguageService service;

    @Autowired
    private LanguageRepository repository;

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Language> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.preserve(createLanguage());

        List<Language> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.preserve(createLanguage());
        repository.preserve(createLanguage());

        List<Language> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByEntityId(fakeId));
    }

    @Test
    public void findById_found() {
        Language saved = repository.preserve(createLanguage());

        assertDoesNotThrow(() -> service.findByEntityId(saved.getEntityId()));
    }

    @Test
    public void findByName_notFound() {
        String fakeName = randomString();

        assertThrows(EntityNotFoundException.class, () -> service.findByName(fakeName));
    }

    @Test
    public void findByName_found() {
        Language saved = repository.preserve(createLanguage());

        assertDoesNotThrow(() -> service.findByName(saved.getName()));
    }

    @Test
    public void save() {
        Language saved = service.save(createLanguage());

        Optional<Language> found = repository.findById(saved.getEntityId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
    }

    @Test
    public void save_nameAlreadyExists() {
    }

    @Test
    public void update() {
        Language saved = createLanguage();
        Long entityId = repository.preserve(saved).getEntityId();
        Language newObj = createLanguage(entityId);

        Language updatedLanguage = service.update(newObj);

        assertEquals(newObj.getEntityId(), updatedLanguage.getEntityId());
        assertEquals(newObj.getName(), updatedLanguage.getName());
    }

    @Test
    public void deleteById_notFound() {
        long fakeId = generateId();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByEntityId(fakeId));
    }

    @Test
    public void deleteById_languageExists() {
        Long entityId = repository.preserve(createLanguage()).getEntityId();

        service.deleteByEntityId(entityId);

        Optional<Language> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }
}

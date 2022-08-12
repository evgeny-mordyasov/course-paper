package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.LanguageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createLanguage;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
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
        repository.saveAndFlush(createLanguage());

        List<Language> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.saveAndFlush(createLanguage());
        repository.saveAndFlush(createLanguage());

        List<Language> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<Language> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Language saved = repository.saveAndFlush(createLanguage());

        Optional<Language> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = randomString();

        Optional<Language> found = service.findByName(fakeName);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_found() {
        Language saved = repository.saveAndFlush(createLanguage());

        Optional<Language> found = service.findByName(saved.getName());

        assertTrue(found.isPresent());
    }

    @Test
    public void save() {
        Language saved = service.save(createLanguage());

        Optional<Language> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getName(), found.get().getName());
    }

    @Test
    public void save_nameAlreadyExists() {
        final String name = randomString();

        repository.saveAndFlush(createLanguage(name));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(createLanguage(name)));
    }

    @Test
    public void update() {
        Language saved = createLanguage();
        Long entityId = repository.saveAndFlush(saved).getId();
        Language newObj = createLanguage(entityId);

        Language updatedLanguage = service.update(newObj);

        assertEquals(newObj.getId(), updatedLanguage.getId());
        assertEquals(newObj.getName(), updatedLanguage.getName());
    }

    @Test
    public void deleteById_notFound() {
        long fakeId = generateId();

        assertThrows(NotFoundException.class, () -> service.deleteById(fakeId));
    }

    @Test
    public void deleteById_languageExists() {
        Long entityId = repository.saveAndFlush(createLanguage()).getId();

        service.deleteById(entityId);

        Optional<Language> found = repository.findById(entityId);

        assertFalse(found.isPresent());
    }
}

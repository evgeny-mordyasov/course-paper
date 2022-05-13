package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.ClientRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createClient;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
public class ClientServiceTest {
    @Autowired
    private ClientService service;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Client> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.save(createClient());

        List<Client> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.save(createClient());
        repository.save(createClient());

        List<Client> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<Client> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Client saved = repository.save(createClient());

        Optional<Client> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void findByEmail_notFound() {
        String fakeEmail = randomString();

        Optional<Client> found = service.findByEmail(fakeEmail);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByEmail_found() {
        Client saved = repository.save(createClient());

        Optional<Client> found = service.findByEmail(saved.getEmail());

        assertTrue(found.isPresent());
    }

    @Test
    public void save() {
        Client saved = createClient();
        service.save(saved);

        Optional<Client> found = repository.findByEmail(saved.getEmail());

        assertTrue(found.isPresent());
        assertEquals(saved.getSurname(), found.get().getSurname());
        assertEquals(saved.getName(), found.get().getName());
        assertEquals(saved.getPatronymic(), found.get().getPatronymic());
        assertEquals(saved.getEmail(), found.get().getEmail());
    }

    @Test
    public void save_emailAlreadyExists() {
        final String email = randomString();

        repository.save(createClient(email));

        assertThrows(DataIntegrityViolationException.class, () -> service.save(createClient(email)));
    }

    @Test
    public void update() {
        Client saved = createClient();
        Long entityId = repository.save(saved).getId();
        Client newObj = createClient(entityId);

        service.update(newObj);
        Optional<Client> found = repository.findById(entityId);

        assertTrue(found.isPresent());
        assertEquals(newObj.getId(), found.get().getId());
        assertEquals(newObj.getSurname(), found.get().getSurname());
        assertEquals(newObj.getName(), found.get().getName());
        assertEquals(newObj.getPatronymic(), found.get().getPatronymic());
        assertTrue(encoder.matches(newObj.getPassword(), found.get().getPassword()));
        assertEquals(saved.getEmail(), found.get().getEmail());
        assertEquals(saved.getRole(), found.get().getRole());
        assertEquals(saved.isActive(), found.get().isActive());
    }

    @Test
    public void update_clientDoesExistById() {
        Long fakeId = generateId();
        Client client = createClient(fakeId);

        assertThrows(NotFoundException.class, () -> service.update(client));
    }

    @Test
    public void deleteById_clientExists() {
        Long entityId = repository.save(createClient()).getId();

        service.deleteById(entityId);

        Optional<Client> found = repository.findById(entityId);

        assertTrue(found.isPresent());
        assertFalse(found.get().isActive());
    }
}

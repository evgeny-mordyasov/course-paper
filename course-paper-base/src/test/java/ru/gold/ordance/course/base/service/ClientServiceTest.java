package ru.gold.ordance.course.base.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import ru.gold.ordance.course.base.TestConfiguration;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.repository.sub.ClientRepository;
import ru.gold.ordance.course.base.service.core.sub.ClientService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.course.base.EntityGenerator.createClient;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;

@DataJpaTest(showSql = false)
@ContextConfiguration(classes = TestConfiguration.class)
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
        repository.preserve(createClient());

        List<Client> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.preserve(createClient());
        repository.preserve(createClient());

        List<Client> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = generateId();

        Optional<Client> client = service.findByEntityId(fakeId);

        assertTrue(client.isEmpty());
    }

    @Test
    public void findById_found() {
        Client saved = repository.preserve(createClient());

        Optional<Client> client = service.findByEntityId(saved.getEntityId());

        assertTrue(client.isPresent());
    }

    @Test
    public void findByEmail_notFound() {
        String fakeEmail = randomString();

        Optional<Client> client = service.findByEmail(fakeEmail);

        assertTrue(client.isEmpty());
    }

    @Test
    public void findByEmail_found() {
        Client saved = repository.preserve(createClient());

        Optional<Client> client = service.findByEmail(saved.getEmail());

        assertTrue(client.isPresent());
    }

    @Test
    public void save() {
        Client saved = service.save(createClient());

        Optional<Client> found = repository.findById(saved.getEntityId());

        assertTrue(found.isPresent());
        assertEquals(saved.getSurname(), found.get().getSurname());
        assertEquals(saved.getName(), found.get().getName());
        assertEquals(saved.getPatronymic(), found.get().getPatronymic());
        assertEquals(saved.getEmail(), found.get().getEmail());
    }

    @Test
    public void save_emailAlreadyExists() {
    }

    @Test
    public void update() {
        Client saved = createClient();
        Long entityId = repository.preserve(saved).getEntityId();
        Client newObj = createClient(entityId);

        Client updatedClient = service.update(newObj);

        assertEquals(newObj.getEntityId(), updatedClient.getEntityId());
        assertEquals(newObj.getSurname(), updatedClient.getSurname());
        assertEquals(newObj.getName(), updatedClient.getName());
        assertEquals(newObj.getPatronymic(), updatedClient.getPatronymic());
        assertTrue(encoder.matches(newObj.getPassword(), updatedClient.getPassword()));
        assertEquals(saved.getEmail(), updatedClient.getEmail());
        assertEquals(saved.getRole(), updatedClient.getRole());
        assertEquals(saved.getIsActive(), updatedClient.getIsActive());
    }

    @Test
    public void deleteById_notFound() {
        long fakeId = generateId();

        assertThrows(EntityNotFoundException.class, () -> service.deleteByEntityId(fakeId));
    }

    @Test
    public void deleteById_clientExists() {
        Long entityId = repository.preserve(createClient()).getEntityId();

        service.deleteByEntityId(entityId);

        Optional<Client> found = repository.findById(entityId);

        assertTrue(found.isPresent());
        assertFalse(found.get().getIsActive());
    }
}

package ru.gold.ordance.course.base.service.core;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.repository.sub.ClientRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.persistence.repository.main.EntityRepository.getEntity;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClientService {
    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public ClientService(ClientRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Optional<Client> findByEntityId(Long entityId) {
        return repository.findByEntityId(entityId);
    }

    public Optional<Client> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Client save(Client client) {
        Client fullClient = client.toBuilder()
                .withPassword(encoder.encode(client.getPassword()))
                .build();

        return repository.preserve(fullClient);
    }

    public Client update(Client client) {
        Client clientFromDb = getEntity(repository.findByEntityId(client.getEntityId()));

        Client updatedClient = clientFromDb.toBuilder()
                .withSurname(client.getSurname())
                .withName(client.getName())
                .withPatronymic(client.getPatronymic())
                .withPassword(getNewPasswordIfChanged(client, clientFromDb))
                .withLastModifiedDate(LocalDateTime.now(ZoneOffset.UTC))
                .withIsActive(client.getIsActive() == null ? clientFromDb.getIsActive() : client.getIsActive())
                .build();

        return repository.update(updatedClient);
    }

    private String getNewPasswordIfChanged(Client newClient, Client fromDatabase) {
        if (!encoder.matches(newClient.getPassword(), fromDatabase.getPassword())
                && !newClient.getPassword().equals(fromDatabase.getPassword())) {
            return encoder.encode(newClient.getPassword());
        }

        return fromDatabase.getPassword();
    }

    public void deleteByEntityId(Long entityId) {
        Client clientFromDb = getEntity(repository.findByEntityId(entityId));

        Client updatedClient = clientFromDb.toBuilder()
                .withLastModifiedDate(LocalDateTime.now(ZoneOffset.UTC))
                .withIsActive(false)
                .build();

        repository.saveAndFlush(updatedClient);
    }
}

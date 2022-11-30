package ru.gold.ordance.course.base.service.core.sub.impl;

import com.sun.istack.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.service.core.sub.ClientService;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.repository.sub.ClientRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.common.utils.TestUtils.not;

@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    private final PasswordEncoder encoder;

    public ClientServiceImpl(ClientRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> findByEntityId(@NotNull Long entityId) {
        return repository.findByEntityId(entityId);
    }

    @Override
    public Optional<Client> findByEmail(@NotNull String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Client save(@NotNull Client client) {
        Client fullClient = client.toBuilder()
                .withPassword(encoder.encode(client.getPassword()))
                .build();

        return repository.preserve(fullClient);
    }

    @Override
    public Client update(@NotNull Client client) {
        Client clientFromDb = repository.findByEntityId(client.getEntityId())
                .orElseThrow(EntityNotFoundException::new);

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
        if (not(encoder.matches(newClient.getPassword(), fromDatabase.getPassword()))
                && not(newClient.getPassword().equals(fromDatabase.getPassword()))) {
            return encoder.encode(newClient.getPassword());
        }

        return fromDatabase.getPassword();
    }

    @Override
    public void deleteByEntityId(@NotNull Long entityId) {
        Client clientFromDb = repository.findByEntityId(entityId)
                .orElseThrow(EntityNotFoundException::new);

        Client updatedClient = clientFromDb.toBuilder()
                .withLastModifiedDate(LocalDateTime.now(ZoneOffset.UTC))
                .withIsActive(false)
                .build();

        repository.saveAndFlush(updatedClient);
    }
}

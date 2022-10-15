package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.Role;
import ru.gold.ordance.course.base.persistence.repository.ClientRepository;
import ru.gold.ordance.course.base.service.ClientService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
    public Client findByEntityId(@NotNull Long entityId) {
        return repository.getByEntityId(entityId);
    }

    @Override
    public Client findByEmail(@NotNull String email) {
        return repository.getByEmail(email);
    }

    @Override
    public Client save(@NotNull Client client) {
        Client fullClient = client.toBuilder()
                .withPassword(encoder.encode(client.getPassword()))
                .withRole(Role.USER)
                .withIsActive(true)
                .withStartDate(new Timestamp(new Date().getTime()))
                .withUpdateDate(new Timestamp(new Date().getTime()))
                .build();

        return repository.preserve(fullClient);
    }

    @Override
    public Client update(@NotNull Client client) {
        Client clientFromDb = repository.getByEntityId(client.getEntityId());

        Client updatedClient = clientFromDb.toBuilder()
                .withSurname(client.getSurname())
                .withName(client.getName())
                .withPatronymic(client.getPatronymic())
                .withPassword(getNewPasswordIfChanged(client, clientFromDb))
                .withUpdateDate(new Timestamp(new Date().getTime()))
                .build();

        return repository.update(updatedClient);
    }

    private String getNewPasswordIfChanged(Client newClient, Client fromDatabase) {
        if (not(encoder.matches(newClient.getPassword(), fromDatabase.getPassword()))) {
            return encoder.encode(newClient.getPassword());
        }

        return fromDatabase.getPassword();
    }

    @Override
    public void deleteByEntityId(@NotNull Long entityId) {
        repository.deleteByEntityId(entityId);
    }
}

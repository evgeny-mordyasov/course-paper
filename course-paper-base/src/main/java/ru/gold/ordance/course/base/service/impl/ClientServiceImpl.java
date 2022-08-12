package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.Role;
import ru.gold.ordance.course.base.exception.NotFoundException;
import ru.gold.ordance.course.base.persistence.ClientRepository;
import ru.gold.ordance.course.base.service.ClientService;

import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.common.utils.TestUtils.not;

@Service
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
    public Optional<Client> findById(@NotNull Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Client> findByEmail(@NotNull String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Client save(@NotNull Client client) {
        Client clientWithHashPassword = client.toBuilder()
                .withPassword(encoder.encode(client.getPassword()))
                .withRole(Role.USER)
                .withIsActive(true)
                .build();

        return repository.saveAndFlush(clientWithHashPassword);
    }

    @Override
    public Client update(@NotNull Client client) {
        Client clientFromDb = repository.getById(client.getId());

        Client updatedClient = clientFromDb.toBuilder()
                .withSurname(client.getSurname())
                .withName(client.getName())
                .withPatronymic(client.getPatronymic())
                .withPassword(getNewPasswordIfChanged(client, clientFromDb))
                .build();

        return repository.saveAndFlush(updatedClient);
    }

    private String getNewPasswordIfChanged(Client newClient, Client fromDatabase) {
        if (not(encoder.matches(newClient.getPassword(), fromDatabase.getPassword()))) {
            return encoder.encode(newClient.getPassword());
        }

        return fromDatabase.getPassword();
    }

    @Override
    public void deleteById(@NotNull Long id) {
        Client client = repository.findById(id)
                .orElseThrow(NotFoundException::new);

        inactive(client);
    }

    private void inactive(Client client) {
        Client deletedClient = client.toBuilder()
                .withIsActive(false)
                .build();

        repository.saveAndFlush(deletedClient);
    }
}

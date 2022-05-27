package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.Role;
import ru.gold.ordance.course.base.persistence.ClientRepository;
import ru.gold.ordance.course.base.service.ClientService;

import java.util.List;
import java.util.Optional;

import static ru.gold.ordance.course.common.utils.TestUtils.not;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClientServiceImpl implements ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

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
    public void update(@NotNull Client client) {
        Optional<Client> found = repository.findById(client.getId());
        found.ifPresent(f -> {
            Client.ClientBuilder updatedClient = client.toBuilder()
                    .withEmail(f.getEmail())
                    .withRole(f.getRole())
                    .withIsActive(f.isActive());

            updatePasswordIfChanged(client, f, updatedClient);

            repository.saveAndFlush(updatedClient.build());
        });
    }

    @Override
    public void deleteById(@NotNull Long id) {
        Optional<Client> found = repository.findById(id);
        found.ifPresent(c -> {
            Client deletedClient = c.toBuilder()
                    .withIsActive(false)
                    .build();

            repository.saveAndFlush(deletedClient);
        });
    }

    private void updatePasswordIfChanged(Client newClient, Client fromDatabase, Client.ClientBuilder result) {
        if (not(encoder.matches(newClient.getPassword(), fromDatabase.getPassword()))) {
            result.withPassword(encoder.encode(newClient.getPassword()));
        }
    }
}

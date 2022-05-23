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
        LOGGER.info("The search for all clients has started.");

        List<Client> clients = repository.findAll();

        LOGGER.info("Size of list: {}", clients.size());

        return clients;
    }

    @Override
    public Optional<Client> findById(@NotNull Long id) {
        LOGGER.info("The search by id client has started.");

        Optional<Client> client = repository.findById(id);

        if (client.isEmpty()) {
            LOGGER.info("The client not found. entityId = {}", id);
        } else {
            LOGGER.info("The client was found. client = {}", client.get());
        }

        return client;
    }

    @Override
    public Optional<Client> findByEmail(@NotNull String email) {
        LOGGER.info("The search by email client has started.");

        Optional<Client> client = repository.findByEmail(email);

        if (client.isEmpty()) {
            LOGGER.info("The client not found. email = {}", email);
        } else {
            LOGGER.info("The client was found. client = {}", client.get());
        }

        return client;
    }

    @Override
    public Client save(@NotNull Client client) {
        LOGGER.info("The save client has started.");

        Client clientWithHashPassword = client.toBuilder()
                .withPassword(encoder.encode(client.getPassword()))
                .withRole(Role.USER)
                .withIsActive(true)
                .build();
        Client saved = repository.saveAndFlush(clientWithHashPassword);

        LOGGER.info("The save client has finished.");

        return saved;
    }

    @Override
    @SuppressWarnings("all")
    public void update(@NotNull Client client) {
        LOGGER.info("The update client has started.");

        Client found = repository.findById(client.getId()).get();

        Client.ClientBuilder updatedClient = client.toBuilder()
                .withEmail(found.getEmail())
                .withRole(found.getRole())
                .withIsActive(found.isActive());
        if (not(encoder.matches(client.getPassword(), found.getPassword()))) {
            updatedClient.withPassword(encoder.encode(client.getPassword()));
        }

        repository.saveAndFlush(updatedClient.build());

        LOGGER.info("The update client has finished.");
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("The delete client has started.");

        Optional<Client> found = repository.findById(id);

        if (found.isPresent()) {
            Client deletedClient = found.get()
                    .toBuilder()
                    .withIsActive(false)
                    .build();
            repository.saveAndFlush(deletedClient);

            LOGGER.info("The client was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The client by id does not exist. entityId = {}", id);
        }
    }
}

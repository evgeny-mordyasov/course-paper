package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import java.util.Optional;

@Repository
public interface ClientRepository extends EntityRepository<Client> {
    Optional<Client> findByEmail(String email);

    default Client getByEmail(String email) {
        Optional<Client> entity = findByEmail(email);

        if (entity.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return entity.get();
    }

    @Override
    default Client preserve(Client entity) {
        validate(entity);
        return EntityRepository.super.preserve(entity);
    }

    @Override
    default Client update(Client entity) {
        validate(entity);
        return EntityRepository.super.update(entity);
    }

    @Override
    default void deleteByEntityId(Long entityId) {
        Optional<Client> client = findByEntityId(entityId);

        if (client.isEmpty()) {
            throw new EntityNotFoundException();
        }

        inactive(client.get());
    }

    private void inactive(Client client) {
        Client deletedClient = client.toBuilder()
                .withIsActive(false)
                .build();

        saveAndFlush(deletedClient);
    }

    private void validate(Client entity) {
        Optional<Client> fromStorage = findByEmail(entity.getEmail());

        fromStorage.ifPresent(clientFromStorage -> {
            if (!clientFromStorage.getEntityId().equals(entity.getEntityId())) {
                throw new ViolatesConstraintException();
            }
        });
    }
}

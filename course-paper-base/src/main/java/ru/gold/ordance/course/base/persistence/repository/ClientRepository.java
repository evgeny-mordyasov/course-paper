package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Client;

import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getEntity;

@Repository
public interface ClientRepository extends EntityDuplicateRepository<Client> {
    Optional<Client> findByEmail(String email);

    default Client getByEmail(String email) {
        return getEntity(findByEmail(email));
    }

    default Client preserve(Client entity) {
        return preserve(entity, () -> findByEmail(entity.getEmail()));
    }

    @Override
    default Client update(Client entity) {
        return update(entity, () -> findByEmail(entity.getEmail()));
    }

    @Override
    default void actionForDeleting(Client client) {
        Client deletedClient = client.toBuilder()
                .withIsActive(false)
                .build();

        saveAndFlush(deletedClient);
    }
}

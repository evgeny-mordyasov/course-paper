package ru.gold.ordance.course.persistence.repository.sub;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.repository.main.NonDuplicateEntityRepository;

import java.util.Optional;

@Repository
public interface ClientRepository extends NonDuplicateEntityRepository<Client> {
    Optional<Client> findByEmail(String email);

    default Client preserve(Client entity) {
        return preserve(entity, () -> findByEmail(entity.getEmail()));
    }

    default Client update(Client entity) {
        return update(entity, () -> findByEmail(entity.getEmail()));
    }
}

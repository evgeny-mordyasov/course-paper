package ru.gold.ordance.course.base.persistence;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends EntityRepository<Client> {
    Optional<Client> findByEmail(String email);
}

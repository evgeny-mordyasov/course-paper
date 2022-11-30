package ru.gold.ordance.course.base.service.core.sub;


import ru.gold.ordance.course.base.service.core.UpdatableEntityService;
import ru.gold.ordance.course.persistence.entity.impl.Client;

import java.util.Optional;

public interface ClientService extends UpdatableEntityService<Client> {
    Optional<Client> findByEmail(String email);
}

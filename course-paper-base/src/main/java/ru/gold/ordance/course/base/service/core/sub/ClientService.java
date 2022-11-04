package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.core.UpdatableEntityService;

public interface ClientService extends UpdatableEntityService<Client> {
    Client getByEmail(String email);
}

package ru.gold.ordance.course.web.service.mapper;

import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.web.api.client.ClientUpdateRequest;
import ru.gold.ordance.course.web.api.client.WebClient;

public interface ClientMapper {
    Client toClient(ClientUpdateRequest rq);

    WebClient fromClient(Client client);
}

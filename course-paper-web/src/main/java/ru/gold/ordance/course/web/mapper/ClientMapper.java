package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.web.api.client.ClientUpdateRequest;
import ru.gold.ordance.course.web.api.client.WebClient;

public interface ClientMapper {
    Client toClient(ClientUpdateRequest rq);

    Client toClient(AuthorizationSignUpRequest rq);

    WebClient fromClient(Client client);
}

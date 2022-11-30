package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.request.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.internal.api.request.client.ClientUpdateRequest;
import ru.gold.ordance.course.internal.api.request.client.WebClient;
import ru.gold.ordance.course.persistence.entity.impl.Client;

public interface ClientMapper {
    Client toClient(ClientUpdateRequest rq);

    Client toClient(AuthorizationSignUpRequest rq);

    WebClient fromClient(Client client);
}

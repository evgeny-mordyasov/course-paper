package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignUpRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientUpdateRequest;
import ru.gold.ordance.course.internal.api.domain.client.model.WebClient;
import ru.gold.ordance.course.persistence.entity.impl.Client;

public interface ClientMapper {
    Client toClient(ClientUpdateRequest rq);

    Client toClient(AuthorizationSignUpRequest rq);

    WebClient fromClient(Client client);
}

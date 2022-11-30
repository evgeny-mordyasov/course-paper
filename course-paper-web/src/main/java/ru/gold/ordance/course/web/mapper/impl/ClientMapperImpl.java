package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.internal.api.request.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.internal.api.request.client.ClientUpdateRequest;
import ru.gold.ordance.course.internal.api.request.client.WebClient;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.web.mapper.ClientMapper;

public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toClient(ClientUpdateRequest rq) {
        return Client.builder()
                .withEntityId(rq.getEntityId())
                .withSurname(rq.getSurname())
                .withName(rq.getName())
                .withPatronymic(rq.getPatronymic())
                .withPassword(rq.getPassword())
                .build();
    }

    @Override
    public Client toClient(AuthorizationSignUpRequest rq) {
        return Client.builder()
                .withSurname(rq.getSurname())
                .withName(rq.getName())
                .withPatronymic(rq.getPatronymic())
                .withEmail(rq.getEmail())
                .withPassword(rq.getPassword())
                .build();
    }

    @Override
    public WebClient fromClient(Client client) {
        return WebClient.builder()
                .entityId(client.getEntityId())
                .surname(client.getSurname())
                .name(client.getName())
                .patronymic(client.getPatronymic())
                .email(client.getEmail())
                .createdDate(client.getCreatedDate())
                .lastModifiedDate(client.getLastModifiedDate())
                .role(client.getRole())
                .isActive(client.getIsActive())
                .build();
    }
}

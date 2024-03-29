package ru.gold.ordance.course.web.service;

import ru.gold.ordance.course.base.service.core.ClientService;
import ru.gold.ordance.course.internal.api.domain.EmptySuccessfulResponse;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientDeleteByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByEmailRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientUpdateRequest;
import ru.gold.ordance.course.internal.api.domain.client.response.ClientDeleteResponse;
import ru.gold.ordance.course.internal.api.domain.client.response.ClientGetEntityResponse;
import ru.gold.ordance.course.internal.api.domain.client.response.ClientGetListResponse;
import ru.gold.ordance.course.internal.api.domain.client.response.ClientUpdateResponse;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.mapper.ClientMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientWebService implements WebService {
    private final ClientService service;
    private final ClientMapper mapper;

    public ClientWebService(ClientService service) {
        this.service = service;
        this.mapper = ClientMapper.instance();
    }

    public ClientGetListResponse findAll() {
        List<Client> allClients = service.findAll();

        return ClientGetListResponse.success(
                allClients.stream()
                        .map(mapper::fromClient)
                        .collect(Collectors.toList()));
    }

    public Response findById(ClientGetByIdRequest rq) {
        Optional<Client> foundClient = service.findByEntityId(rq.getEntityId());

        return process(foundClient);
    }

    public Response findByEmail(ClientGetByEmailRequest rq) {
        Optional<Client> foundClient = service.findByEmail(rq.getEmail());

        return process(foundClient);
    }

    public ClientUpdateResponse update(ClientUpdateRequest rq) {
        Client updatedClient = service.update(mapper.toClient(rq));

        return ClientUpdateResponse.success(mapper.fromClient(updatedClient));
    }

    public ClientDeleteResponse deleteById(ClientDeleteByIdRequest rq) {
        service.deleteByEntityId(rq.getEntityId());

        return ClientDeleteResponse.success();
    }

    private Response process(Optional<Client> client) {
        return client.isPresent()
                ? ClientGetEntityResponse.success(mapper.fromClient(client.get()))
                : new EmptySuccessfulResponse();
    }
}

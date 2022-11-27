package ru.gold.ordance.course.web.service.web.client;

import ru.gold.ordance.course.persistence.entity.Client;
import ru.gold.ordance.course.base.service.core.sub.ClientService;
import ru.gold.ordance.course.web.api.EmptyResponse;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.mapper.ClientMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientWebServiceImpl implements ClientWebService {
    private final ClientService service;
    private final ClientMapper mapper;

    public ClientWebServiceImpl(ClientService service, ClientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ClientGetListResponse findAll() {
        List<Client> allClients = service.findAll();

        return ClientGetListResponse.success(
                allClients.stream()
                        .map(mapper::fromClient)
                        .collect(Collectors.toList()));
    }

    @Override
    public Response findById(ClientGetByIdRequest rq) {
        Optional<Client> foundClient = service.findByEntityId(rq.getEntityId());

        return process(foundClient);
    }

    @Override
    public Response findByEmail(ClientGetByEmailRequest rq) {
        Optional<Client> foundClient = service.findByEmail(rq.getEmail());

        return process(foundClient);
    }

    @Override
    public ClientUpdateResponse update(ClientUpdateRequest rq) {
        Client updatedClient = service.update(mapper.toClient(rq));

        return ClientUpdateResponse.success(mapper.fromClient(updatedClient));
    }

    @Override
    public ClientDeleteResponse deleteById(ClientDeleteByIdRequest rq) {
        service.deleteByEntityId(rq.getEntityId());

        return ClientDeleteResponse.success();
    }

    private Response process(Optional<Client> client) {
        return client.isPresent()
                ? ClientGetEntityResponse.success(mapper.fromClient(client.get()))
                : new EmptyResponse();
    }
}

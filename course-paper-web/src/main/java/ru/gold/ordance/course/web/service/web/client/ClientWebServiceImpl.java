package ru.gold.ordance.course.web.service.web.client;

import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.ClientService;
import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.mapper.ClientMapper;

import java.util.Collections;
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
    public ClientGetEntityResponse findById(ClientGetByIdRequest rq) {
        Optional<Client> foundClient = service.findByEntityId(rq.getEntityId());

        return search(foundClient);
    }

    @Override
    public ClientGetEntityResponse findByEmail(ClientGetByEmailRequest rq) {
        Optional<Client> foundClient = service.findByEmail(rq.getEmail());

        return search(foundClient);
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

    private ClientGetEntityResponse search(Optional<Client> client) {
        return client.isEmpty()
                ? ClientGetEntityResponse.emptySuccess()
                : ClientGetEntityResponse.success(mapper.fromClient(client.get()));
    }
}

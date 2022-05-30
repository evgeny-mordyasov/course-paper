package ru.gold.ordance.course.web.service.client;

import org.springframework.stereotype.Service;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.ClientService;
import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.service.mapper.ClientMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientWebServiceImpl implements ClientWebService {
    private final ClientService service;

    private final ClientMapper mapper;

    public ClientWebServiceImpl(ClientService service, ClientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ClientGetResponse findAll() {
        List<Client> allClients = service.findAll();

        return ClientGetResponse.success(
                allClients.stream()
                        .map(mapper::fromClient)
                        .collect(Collectors.toList()));
    }

    @Override
    public ClientGetResponse findById(ClientGetByIdRequest rq) {
        Optional<Client> foundClient = service.findById(rq.getEntityId());

        return search(foundClient);
    }

    @Override
    public ClientGetResponse findByEmail(ClientGetByEmailRequest rq) {
        Optional<Client> foundClient = service.findByEmail(rq.getEmail());

        return search(foundClient);
    }

    @Override
    public ClientUpdateResponse update(ClientUpdateRequest rq) {
        service.update(mapper.toClient(rq));

        return ClientUpdateResponse.success();
    }

    @Override
    public ClientDeleteResponse deleteById(ClientDeleteByIdRequest rq) {
        service.deleteById(rq.getEntityId());

        return ClientDeleteResponse.success();
    }

    private ClientGetResponse search(Optional<Client> client) {
        return ClientGetResponse.success(client.isEmpty()
                ? Collections.emptyList()
                : Collections.singletonList(mapper.fromClient(client.get())));
    }
}

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
        List<Client> list = service.findAll();

        if (list.isEmpty()) {
            return ClientGetResponse.success(Collections.emptyList());
        } else {
            List<WebClient> webClients = list.stream()
                    .map(mapper::fromClient)
                    .collect(Collectors.toList());

            return ClientGetResponse.success(webClients);
        }
    }

    @Override
    public ClientGetResponse findById(ClientGetByIdRequest rq) {
        Optional<Client> found = service.findById(rq.getEntityId());

        return search(found);
    }

    @Override
    public ClientGetResponse findByEmail(ClientGetByEmailRequest rq) {
        Optional<Client> found = service.findByEmail(rq.getEmail());

        return search(found);
    }

    @Override
    public ClientUpdateResponse update(ClientUpdateRequest rq) {
        service.update(mapper.toClient(rq));

        return ClientUpdateResponse.success();
    }

    @Override
    public ClientDeleteByIdResponse deleteById(ClientDeleteByIdRequest rq) {
        service.deleteById(rq.getEntityId());

        return ClientDeleteByIdResponse.success();
    }

    private ClientGetResponse search(Optional<Client> found) {
        if (found.isEmpty()) {
            return ClientGetResponse.success(Collections.emptyList());
        } else {
            return ClientGetResponse.success(Collections.singletonList(mapper.fromClient(found.get())));
        }
    }
}

package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientDeleteByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByEmailRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientUpdateRequest;
import ru.gold.ordance.course.web.service.web.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.rest.ClientRestController;
import ru.gold.ordance.course.web.service.web.client.ClientWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.Client.BASE_URL)
public class ClientRestControllerImpl implements ClientRestController {
    private final ClientWebService service;

    public ClientRestControllerImpl(ClientWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(service::findAll);
    }

    @Override
    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() ->
                service.findById(new ClientGetByIdRequest(entityId)));
    }

    @Override
    @GetMapping(params = "email", produces = JSON)
    public Response findByEmail(@RequestParam("email") String email) {
        return execute(() ->
                service.findByEmail(new ClientGetByEmailRequest(email)));
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody ClientUpdateRequest rq) {
        return execute(() -> service.update(rq));
    }

    @Override
    @DeleteMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() -> service.deleteById(new ClientDeleteByIdRequest(entityId)));
    }
}

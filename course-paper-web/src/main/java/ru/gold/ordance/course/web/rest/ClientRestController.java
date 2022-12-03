package ru.gold.ordance.course.web.rest;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientDeleteByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByEmailRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientUpdateRequest;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.service.ClientWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.Client.BASE_URL)
public class ClientRestController {
    private final ClientWebService service;

    public ClientRestController(ClientWebService service) {
        this.service = service;
    }

    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(service::findAll);
    }

    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() ->
                service.findById(new ClientGetByIdRequest(entityId)));
    }

    @GetMapping(params = "email", produces = JSON)
    public Response findByEmail(@RequestParam("email") String email) {
        return execute(() ->
                service.findByEmail(new ClientGetByEmailRequest(email)));
    }

    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody ClientUpdateRequest rq) {
        return execute(() -> service.update(rq));
    }

    @DeleteMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() -> service.deleteById(new ClientDeleteByIdRequest(entityId)));
    }
}

package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.client.ClientDeleteByIdRequest;
import ru.gold.ordance.course.web.api.client.ClientGetByEmailRequest;
import ru.gold.ordance.course.web.api.client.ClientGetByIdRequest;
import ru.gold.ordance.course.web.api.client.ClientUpdateRequest;
import ru.gold.ordance.course.web.rest.ClientRestController;
import ru.gold.ordance.course.web.service.web.client.ClientWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping("/api/v1/clients")
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
    @GetMapping(value = "/{entityId}", produces = JSON)
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
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() -> service.deleteById(new ClientDeleteByIdRequest(entityId)));
    }
}

package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.rest.ClientRestController;
import ru.gold.ordance.course.web.service.client.ClientWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

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
        try {
            return service.findAll();
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        ClientGetByIdRequest rq = new ClientGetByIdRequest(entityId);

        try {
            return service.findById(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @GetMapping(value = "/email/{email}", produces = JSON)
    public Response findByEmail(@PathVariable String email) {
        ClientGetByEmailRequest rq = new ClientGetByEmailRequest(email);

        try {
            return service.findByEmail(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody ClientUpdateRequest rq) {
        try {
            return service.update(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        ClientDeleteByIdRequest rq = new ClientDeleteByIdRequest(entityId);

        try {
            return service.deleteById(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }
}

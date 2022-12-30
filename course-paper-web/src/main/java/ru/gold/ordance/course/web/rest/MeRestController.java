package ru.gold.ordance.course.web.rest;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByIdRequest;
import ru.gold.ordance.course.web.service.ClientWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping("/api/v1/me")
public class MeRestController {
    private final ClientWebService service;

    public MeRestController(ClientWebService service) {
        this.service = service;
    }

    @GetMapping(value = "/{clientId}", produces = JSON)
    public Response signIn(@PathVariable Long clientId) {
        return execute(() -> service.findById(new ClientGetByIdRequest(clientId)));
    }
}

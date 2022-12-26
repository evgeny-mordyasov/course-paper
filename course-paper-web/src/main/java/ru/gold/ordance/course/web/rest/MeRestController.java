package ru.gold.ordance.course.web.rest;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.authorization.request.AuthorizationSignInRequest;
import ru.gold.ordance.course.web.service.AuthorizationWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping("/api/v1/me")
public class MeRestController {
    private final AuthorizationWebService service;

    public MeRestController(AuthorizationWebService service) {
        this.service = service;
    }

    @PostMapping(consumes = JSON, produces = JSON)
    public Response signIn(@RequestBody AuthorizationSignInRequest rq) {
        return execute(() -> service.signIn(rq));
    }
}

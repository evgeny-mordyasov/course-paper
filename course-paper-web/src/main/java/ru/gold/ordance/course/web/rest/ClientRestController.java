package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.internal.api.request.Response;
import ru.gold.ordance.course.internal.api.request.client.ClientUpdateRequest;

public interface ClientRestController {
    Response findAll();
    Response findById(Long entityId);
    Response findByEmail(String email);
    Response update(ClientUpdateRequest rq);
    Response deleteById(Long entityId);
}

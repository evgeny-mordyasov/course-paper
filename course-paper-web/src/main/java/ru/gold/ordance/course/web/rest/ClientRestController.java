package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.client.*;

public interface ClientRestController {
    Response findAll();
    Response findById(Long entityId);
    Response findByEmail(String email);
    Response update(ClientUpdateRequest rq);
    Response deleteById(Long entityId);
}

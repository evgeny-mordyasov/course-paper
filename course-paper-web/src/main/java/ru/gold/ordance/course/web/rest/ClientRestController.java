package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.client.*;

public interface ClientRestController extends AbstractRestController {
    ClientGetResponse findAll();
    ClientGetResponse findById(Long entityId);
    ClientGetResponse findByEmail(String email);
    ClientUpdateResponse update(ClientUpdateRequest rq);
    ClientDeleteByIdResponse deleteById(Long entityId);
}

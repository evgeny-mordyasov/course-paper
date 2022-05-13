package ru.gold.ordance.course.web.service.client;

import ru.gold.ordance.course.web.api.client.*;

public interface ClientWebService {
    ClientGetResponse findAll();
    ClientGetResponse findById(ClientGetByIdRequest rq);
    ClientGetResponse findByEmail(ClientGetByEmailRequest rq);
    ClientUpdateResponse update(ClientUpdateRequest rq);
    ClientDeleteByIdResponse deleteById(ClientDeleteByIdRequest rq);
}

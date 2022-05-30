package ru.gold.ordance.course.web.service.client;

import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.service.WebService;

public interface ClientWebService extends WebService {
    ClientGetResponse findAll();
    ClientGetResponse findById(ClientGetByIdRequest rq);
    ClientGetResponse findByEmail(ClientGetByEmailRequest rq);
    ClientUpdateResponse update(ClientUpdateRequest rq);
    ClientDeleteResponse deleteById(ClientDeleteByIdRequest rq);
}

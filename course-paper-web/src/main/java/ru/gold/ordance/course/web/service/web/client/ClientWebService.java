package ru.gold.ordance.course.web.service.web.client;

import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface ClientWebService extends WebService {
    ClientGetResponse findAll();
    ClientGetResponse findById(ClientGetByIdRequest rq);
    ClientGetResponse findByEmail(ClientGetByEmailRequest rq);
    ClientUpdateResponse update(ClientUpdateRequest rq);
    ClientDeleteResponse deleteById(ClientDeleteByIdRequest rq);
}

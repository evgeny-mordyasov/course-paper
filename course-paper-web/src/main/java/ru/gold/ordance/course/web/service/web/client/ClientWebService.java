package ru.gold.ordance.course.web.service.web.client;

import ru.gold.ordance.course.internal.api.request.Response;
import ru.gold.ordance.course.internal.api.request.client.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface ClientWebService extends WebService {
    ClientGetListResponse findAll();
    Response findById(ClientGetByIdRequest rq);
    Response findByEmail(ClientGetByEmailRequest rq);
    ClientUpdateResponse update(ClientUpdateRequest rq);
    ClientDeleteResponse deleteById(ClientDeleteByIdRequest rq);
}

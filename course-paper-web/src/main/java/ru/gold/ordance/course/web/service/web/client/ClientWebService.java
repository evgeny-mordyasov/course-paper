package ru.gold.ordance.course.web.service.web.client;

import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientDeleteByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByEmailRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientGetByIdRequest;
import ru.gold.ordance.course.internal.api.domain.client.request.ClientUpdateRequest;
import ru.gold.ordance.course.internal.api.domain.client.response.ClientDeleteResponse;
import ru.gold.ordance.course.internal.api.domain.client.response.ClientGetListResponse;
import ru.gold.ordance.course.internal.api.domain.client.response.ClientUpdateResponse;
import ru.gold.ordance.course.web.service.web.WebService;

public interface ClientWebService extends WebService {
    ClientGetListResponse findAll();
    Response findById(ClientGetByIdRequest rq);
    Response findByEmail(ClientGetByEmailRequest rq);
    ClientUpdateResponse update(ClientUpdateRequest rq);
    ClientDeleteResponse deleteById(ClientDeleteByIdRequest rq);
}

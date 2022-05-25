package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.rest.ClientRestController;
import ru.gold.ordance.course.web.service.client.ClientWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientRestControllerImpl implements ClientRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestControllerImpl.class);

    private final ClientWebService service;

    public ClientRestControllerImpl(ClientWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public Response findAll() {
        try {
            LOGGER.info("Get all received.");

            Response rs = service.findAll();
            loggingSuccessResponse(rs);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        ClientGetByIdRequest rq = new ClientGetByIdRequest(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            rq.validate();
            Response rs = service.findById(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/email/{email}", produces = JSON)
    public Response findByEmail(@PathVariable String email) {
        ClientGetByEmailRequest rq = new ClientGetByEmailRequest(email);

        try {
            LOGGER.info("Get by email request received: {}", rq);

            rq.validate();
            Response rs = service.findByEmail(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody ClientUpdateRequest rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            rq.validate();
            Response rs = service.update(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        ClientDeleteByIdRequest rq = new ClientDeleteByIdRequest(entityId);

        try {
            LOGGER.info("Delete by id request received: {}", rq);

            rq.validate();
            Response rs = service.deleteById(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }
}

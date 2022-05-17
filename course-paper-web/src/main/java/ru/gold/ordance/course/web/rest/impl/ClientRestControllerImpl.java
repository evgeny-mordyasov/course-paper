package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.client.*;
import ru.gold.ordance.course.web.rest.ClientRestController;
import ru.gold.ordance.course.web.service.client.ClientWebService;
import ru.gold.ordance.course.web.validate.Validator;

import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientRestControllerImpl implements ClientRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestControllerImpl.class);

    private final ClientWebService service;

    private Validator validator;

    public ClientRestControllerImpl(ClientWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public ClientGetResponse findAll() {
        try {
            LOGGER.info("Get all received.");

            ClientGetResponse rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientGetResponse rs = ClientGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public ClientGetResponse findById(@PathVariable Long entityId) {
        ClientGetByIdRequest rq = new ClientGetByIdRequest(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            validate(rq);
            ClientGetResponse rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientGetResponse rs = ClientGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/email/{email}", produces = JSON)
    public ClientGetResponse findByEmail(@PathVariable String email) {
        ClientGetByEmailRequest rq = new ClientGetByEmailRequest(email);

        try {
            LOGGER.info("Get by email request received: {}", rq);

            validate(rq);
            ClientGetResponse rs = service.findByEmail(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientGetResponse rs = ClientGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public ClientUpdateResponse update(@RequestBody ClientUpdateRequest rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            ClientUpdateResponse rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientUpdateResponse rs = ClientUpdateResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public ClientDeleteByIdResponse deleteById(@PathVariable Long entityId) {
        ClientDeleteByIdRequest rq = new ClientDeleteByIdRequest(entityId);

        try {
            LOGGER.info("Delete by id request received: {}", rq);

            validate(rq);
            ClientDeleteByIdResponse rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientDeleteByIdResponse rs = ClientDeleteByIdResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    private void validate(Request rq) {
        validator.validate(rq);
    }
}

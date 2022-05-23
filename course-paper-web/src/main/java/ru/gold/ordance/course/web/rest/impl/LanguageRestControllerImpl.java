package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.rest.LanguageRestController;
import ru.gold.ordance.course.web.service.language.LanguageWebService;

import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/languages")
public class LanguageRestControllerImpl implements LanguageRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageRestControllerImpl.class);

    private final LanguageWebService service;

    public LanguageRestControllerImpl(LanguageWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public LanguageGetResponse findAll() {
        try {
            LOGGER.info("Get all received.");

            LanguageGetResponse rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LanguageGetResponse rs = LanguageGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public LanguageGetResponse findById(@PathVariable Long entityId) {
        LanguageGetByIdRequest rq = new LanguageGetByIdRequest(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            rq.validate();
            LanguageGetResponse rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LanguageGetResponse rs = LanguageGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/name/{name}", produces = JSON)
    public LanguageGetResponse findByName(@PathVariable String name) {
        LanguageGetByNameRequest rq = new LanguageGetByNameRequest(name);

        try {
            LOGGER.info("Get by name request received: {}", rq);

            rq.validate();
            LanguageGetResponse rs = service.findByName(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LanguageGetResponse rs = LanguageGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(consumes = JSON, produces = JSON)
    public LanguageSaveResponse save(@RequestBody LanguageSaveRequest rq) {
        try {
            LOGGER.info("Save request received: {}", rq);

            rq.validate();
            LanguageSaveResponse rs = service.save(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LanguageSaveResponse rs = LanguageSaveResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public LanguageUpdateResponse update(@RequestBody LanguageUpdateRequest rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            rq.validate();
            LanguageUpdateResponse rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LanguageUpdateResponse rs = LanguageUpdateResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public LanguageDeleteByIdResponse deleteById(@PathVariable Long entityId) {
        LanguageDeleteByIdRequest rq = new LanguageDeleteByIdRequest(entityId);

        try {
            LOGGER.info("Delete by id request received: {}", rq);

            rq.validate();
            LanguageDeleteByIdResponse rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LanguageDeleteByIdResponse rs = LanguageDeleteByIdResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }
}

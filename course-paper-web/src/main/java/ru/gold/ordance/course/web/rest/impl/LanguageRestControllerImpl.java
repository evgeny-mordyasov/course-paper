package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.rest.LanguageRestController;
import ru.gold.ordance.course.web.service.language.LanguageWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
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
        LanguageGetByIdRequest rq = new LanguageGetByIdRequest(entityId);

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
    @GetMapping(value = "/name/{name}", produces = JSON)
    public Response findByName(@PathVariable String name) {
        LanguageGetByNameRequest rq = new LanguageGetByNameRequest(name);

        try {
            LOGGER.info("Get by name request received: {}", rq);

            rq.validate();
            Response rs = service.findByName(rq);
            loggingSuccessResponse(rs, rq);

            return rs;
        } catch (Exception e) {
            Response rs = createFrom(e);
            loggingErrorResponse(rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody LanguageSaveRequest rq) {
        try {
            LOGGER.info("Save request received: {}", rq);

            rq.validate();
            Response rs = service.save(rq);
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
    public Response update(@RequestBody LanguageUpdateRequest rq) {
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
        LanguageDeleteByIdRequest rq = new LanguageDeleteByIdRequest(entityId);

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

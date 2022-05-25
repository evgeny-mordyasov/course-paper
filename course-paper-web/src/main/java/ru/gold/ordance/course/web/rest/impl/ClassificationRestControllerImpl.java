package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.rest.ClassificationRestController;
import ru.gold.ordance.course.web.service.classification.ClassificationWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/classifications")
public class ClassificationRestControllerImpl implements ClassificationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationRestControllerImpl.class);

    private final ClassificationWebService service;

    public ClassificationRestControllerImpl(ClassificationWebService service) {
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
        ClassificationGetByIdRequest rq = new ClassificationGetByIdRequest(entityId);

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
        ClassificationGetByNameRequest rq = new ClassificationGetByNameRequest(name);

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
    public Response save(@RequestBody ClassificationSaveRequest rq) {
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
    public Response update(@RequestBody ClassificationUpdateRequest rq) {
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
        ClassificationDeleteByIdRequest rq = new ClassificationDeleteByIdRequest(entityId);

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

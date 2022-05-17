package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.rest.ClassificationRestController;
import ru.gold.ordance.course.web.service.classification.ClassificationWebService;
import ru.gold.ordance.course.web.validate.Validator;

import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/classifications")
public class ClassificationRestControllerImpl implements ClassificationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationRestControllerImpl.class);

    private final ClassificationWebService service;

    private Validator validator;

    public ClassificationRestControllerImpl(ClassificationWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public ClassificationGetResponse findAll() {
        try {
            LOGGER.info("Get all received.");

            ClassificationGetResponse rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClassificationGetResponse rs = ClassificationGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public ClassificationGetResponse findById(@PathVariable Long entityId) {
        ClassificationGetByIdRequest rq = new ClassificationGetByIdRequest(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            validate(rq);
            ClassificationGetResponse rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClassificationGetResponse rs = ClassificationGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/name/{name}", produces = JSON)
    public ClassificationGetResponse findByName(@PathVariable String name) {
        ClassificationGetByNameRequest rq = new ClassificationGetByNameRequest(name);

        try {
            LOGGER.info("Get by name request received: {}", rq);

            validate(rq);
            ClassificationGetResponse rs = service.findByName(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClassificationGetResponse rs = ClassificationGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(consumes = JSON, produces = JSON)
    public ClassificationSaveResponse save(@RequestBody ClassificationSaveRequest rq) {
        try {
            LOGGER.info("Save request received: {}", rq);

            validate(rq);
            ClassificationSaveResponse rs = service.save(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClassificationSaveResponse rs = ClassificationSaveResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public ClassificationUpdateResponse update(@RequestBody ClassificationUpdateRequest rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            ClassificationUpdateResponse rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClassificationUpdateResponse rs = ClassificationUpdateResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public ClassificationDeleteByIdResponse deleteById(@PathVariable Long entityId) {
        ClassificationDeleteByIdRequest rq = new ClassificationDeleteByIdRequest(entityId);

        try {
            LOGGER.info("Delete by id request received: {}", rq);

            validate(rq);
            ClassificationDeleteByIdResponse rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClassificationDeleteByIdResponse rs = ClassificationDeleteByIdResponse.error(status.getCode(), status.getDescription());
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

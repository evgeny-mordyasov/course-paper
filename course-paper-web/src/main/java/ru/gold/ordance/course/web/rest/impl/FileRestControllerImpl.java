package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.Status;
import ru.gold.ordance.course.web.api.file.FileGetResponse;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.FileSaveResponse;
import ru.gold.ordance.course.web.rest.FileRestController;
import ru.gold.ordance.course.web.service.file.FileWebService;
import ru.gold.ordance.course.web.validate.Validator;

import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/files")
public class FileRestControllerImpl implements FileRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRestControllerImpl.class);

    private final FileWebService service;

    private Validator validator;

    public FileRestControllerImpl(FileWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public FileGetResponse findAll() {
        try {
            LOGGER.info("Get all received.");

            FileGetResponse rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            FileGetResponse rs = FileGetResponse.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @PostMapping(produces = JSON)
    public FileSaveResponse save(@RequestParam("file") MultipartFile file,
                                 @RequestParam("languageId") Long languageId,
                                 @RequestParam("classificationId") Long classificationId) {
        FileSaveRequest rq = FileSaveRequest.builder()
                .file(file)
                .languageId(languageId)
                .classificationId(classificationId)
                .build();

        try {
            LOGGER.info("Save request received: {}", rq);

            validate(rq);
            FileSaveResponse rs = service.save(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            FileSaveResponse rs = FileSaveResponse.error(status.getCode(), status.getDescription());
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

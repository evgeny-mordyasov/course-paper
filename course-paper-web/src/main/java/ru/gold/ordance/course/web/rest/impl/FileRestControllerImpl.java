package ru.gold.ordance.course.web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.rest.FileRestController;
import ru.gold.ordance.course.web.service.file.FileWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/files")
public class FileRestControllerImpl implements FileRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileRestControllerImpl.class);

    private final FileWebService service;

    public FileRestControllerImpl(FileWebService service) {
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
    @PostMapping(produces = JSON)
    public Response save(@RequestParam("file") MultipartFile file,
                         @RequestParam("languageId") Long languageId,
                         @RequestParam("classificationId") Long classificationId) {
        FileSaveRequest rq = FileSaveRequest.builder()
                .file(file)
                .languageId(languageId)
                .classificationId(classificationId)
                .build();

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
}

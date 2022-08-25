package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.rest.FileRestController;
import ru.gold.ordance.course.web.service.web.file.FileWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/files")
public class FileRestControllerImpl implements FileRestController {
    private final FileWebService service;

    public FileRestControllerImpl(FileWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public Response findAll() {
        try {
            return service.findAll();
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PostMapping(params = {"file", "languageId", "classificationId"}, produces = JSON)
    public Response save(@RequestParam("file") MultipartFile file,
                         @RequestParam("languageId") Long languageId,
                         @RequestParam("classificationId") Long classificationId) {
        FileSaveRequest rq = FileSaveRequest.builder()
                .file(file)
                .languageId(languageId)
                .classificationId(classificationId)
                .build();

        try {
            return service.save(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    public Response deleteByUrn(@RequestBody FileDeleteByUrnRequest rq) {
        try {
            return service.deleteByUrn(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }
}

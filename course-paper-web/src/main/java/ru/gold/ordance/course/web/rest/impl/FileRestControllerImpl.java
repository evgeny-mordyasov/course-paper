package ru.gold.ordance.course.web.rest.impl;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;
import ru.gold.ordance.course.web.api.file.FileGetByIdRequest;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.rest.FileRestController;
import ru.gold.ordance.course.web.service.web.file.FileWebService;

import java.io.IOException;
import java.util.Optional;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

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
        return execute(service::findAll);
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

        return execute(() -> {
            try {
                return service.save(rq);
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }

    @Override
    @GetMapping(value = "/resource/{entityId}")
    public ResponseEntity<?> findResourceById(@PathVariable("entityId") Long entityId) {
        FileGetByIdRequest rq = new FileGetByIdRequest(entityId);

        try {
            Resource file = service.load(rq);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.of(Optional.of(createFrom(e)));
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() -> service.findById(new FileGetByIdRequest(entityId)));
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    public Response deleteByUrn(@RequestBody FileDeleteByUrnRequest rq) {
        return execute(() -> {
            try {
                return service.deleteByUrn(rq);
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }
}

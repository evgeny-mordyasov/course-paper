package ru.gold.ordance.course.web.rest.impl;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.*;
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
    @PatchMapping(produces = JSON)
    public Response patch(@RequestParam("file") MultipartFile file,
                          @RequestParam("documentId") Long documentId,
                          @RequestParam("languageId") Long languageId) {
        FilePatchRequest rq = FilePatchRequest.builder()
                .file(file)
                .documentId(documentId)
                .languageId(languageId)
                .build();

        return execute(() -> {
            try {
                return service.patch(rq);
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }

    @Override
    @GetMapping(value = "/resource")
    public ResponseEntity<?> findResourceById(@RequestParam(name = "documentId") Long documentId,
                                              @RequestParam(name = "languageId") Long languageId) {
        FileGetByIdAndLanguageIdRequest rq = new FileGetByIdAndLanguageIdRequest(documentId, languageId);

        try {
            Resource file = service.load(rq);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + file.getFilename() + "\"")
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
    @DeleteMapping(produces = JSON, consumes = JSON)
    public Response deleteByUrn(@RequestBody FileDeleteByUrnRequest rq) {
        return execute(() -> {
            try {
                return service.deleteByUrn(rq);
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() -> {
            try {
                return service.deleteById(new FileDeleteByIdRequest(entityId));
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }
}

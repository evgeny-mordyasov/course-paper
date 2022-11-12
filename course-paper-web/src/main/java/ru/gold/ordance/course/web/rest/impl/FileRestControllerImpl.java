package ru.gold.ordance.course.web.rest.impl;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.service.web.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.api.history.HistorySaveRequest;
import ru.gold.ordance.course.web.rest.FileRestController;
import ru.gold.ordance.course.web.service.web.file.FileWebService;
import ru.gold.ordance.course.web.service.web.history.HistoryWebService;

import java.io.IOException;
import java.util.Optional;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.File.BASE_URL)
public class FileRestControllerImpl implements FileRestController {
    private final FileWebService fileService;
    private final HistoryWebService historyService;

    public FileRestControllerImpl(FileWebService fileService, HistoryWebService historyService) {
        this.fileService = fileService;
        this.historyService = historyService;
    }

    @Override
    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(fileService::findAll);
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
                return fileService.save(rq);
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
                return fileService.patch(rq);
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }

    @Override
    @GetMapping(value = Endpoint.File.RESOURCE)
    public ResponseEntity<?> findResource(@RequestParam(name = "clientId") Long clientId,
                                          @RequestParam(name = "documentId") Long documentId,
                                          @RequestParam(name = "languageId") Long languageId) {
        try {
            Resource file = fileService.load(new FileGetByIdAndLanguageIdRequest(documentId, languageId));
            historyService.save(new HistorySaveRequest(clientId, documentId, languageId));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.of(Optional.of(createFrom(e)));
        }
    }

    @Override
    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() -> fileService.findById(new FileGetByIdRequest(entityId)));
    }

    @Override
    @GetMapping(value = Endpoint.File.FREE_LANGUAGES, produces = JSON)
    public Response getFreeLanguages(@PathVariable Long documentId) {
        return execute(() -> fileService.getFreeLanguages(new FileGetFreeLanguagesByIdRequest(documentId)));
    }

    @Override
    @DeleteMapping(produces = JSON, consumes = JSON)
    public Response deleteByUrn(@RequestBody FileDeleteByUrnRequest rq) {
        return execute(() -> {
            try {
                return fileService.deleteByUrn(rq);
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }

    @Override
    @DeleteMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() -> {
            try {
                return fileService.deleteById(new FileDeleteByIdRequest(entityId));
            } catch (IOException e) {
                return createFrom(e);
            }
        });
    }
}

package ru.gold.ordance.course.web.rest.impl;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.internal.api.domain.file.request.*;
import ru.gold.ordance.course.internal.api.dto.CustomMultipartFile;
import ru.gold.ordance.course.internal.api.domain.history.request.HistorySaveRequest;
import ru.gold.ordance.course.web.service.web.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.rest.FileRestController;
import ru.gold.ordance.course.web.service.web.file.FileWebService;
import ru.gold.ordance.course.web.service.web.history.HistoryWebService;

import java.io.IOException;
import java.util.Optional;

import static ru.gold.ordance.course.internal.api.domain.BaseErrorResponse.handleException;
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
        return execute(() -> {
            try {
                FileSaveRequest rq = FileSaveRequest.builder()
                        .file(CustomMultipartFile.builder()
                                .withOriginalFilename(file.getOriginalFilename())
                                .withInputStream(file.getInputStream())
                                .withIsEmpty(file.isEmpty())
                                .withSize(file.getSize())
                                .build())
                        .languageId(languageId)
                        .classificationId(classificationId)
                        .build();

                return fileService.save(rq);
            } catch (IOException e) {
                return handleException(e);
            }
        });
    }

    @Override
    @PatchMapping(produces = JSON)
    public Response patch(@RequestParam("file") MultipartFile file,
                          @RequestParam("documentId") Long documentId,
                          @RequestParam("languageId") Long languageId) {
        return execute(() -> {
            try {
                FilePatchRequest rq = FilePatchRequest.builder()
                        .file(CustomMultipartFile.builder()
                                .withOriginalFilename(file.getOriginalFilename())
                                .withInputStream(file.getInputStream())
                                .withIsEmpty(file.isEmpty())
                                .withSize(file.getSize())
                                .build())
                        .documentId(documentId)
                        .languageId(languageId)
                        .build();

                return fileService.patch(rq);
            } catch (IOException e) {
                return handleException(e);
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
            return ResponseEntity.of(Optional.of(handleException(e)));
        }
    }

    @Override
    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() -> fileService.findById(new FileGetByIdRequest(entityId)));
    }

    @Override
    @GetMapping(params = "classificationId", produces = JSON)
    public Response findByClassificationId(@RequestParam("classificationId") Long classificationId) {
        return execute(() -> fileService.getFilesByClassificationId(new FileGetByClassificationIdRequest(classificationId)));
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
                return handleException(e);
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
                return handleException(e);
            }
        });
    }
}

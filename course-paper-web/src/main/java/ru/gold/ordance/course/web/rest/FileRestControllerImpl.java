package ru.gold.ordance.course.web.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.common.utils.FileUtils;
import ru.gold.ordance.course.internal.api.domain.file.request.*;
import ru.gold.ordance.course.internal.api.dto.CustomMultipartFile;
import ru.gold.ordance.course.internal.api.domain.history.request.HistorySaveRequest;
import ru.gold.ordance.course.web.dto.FileResource;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.service.FileWebService;
import ru.gold.ordance.course.web.service.HistoryWebService;

import java.io.IOException;
import java.util.Optional;

import static ru.gold.ordance.course.internal.api.domain.BaseErrorResponse.handleException;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.File.BASE_URL)
public class FileRestControllerImpl {
    private final FileWebService fileService;
    private final HistoryWebService historyService;

    public FileRestControllerImpl(FileWebService fileService, HistoryWebService historyService) {
        this.fileService = fileService;
        this.historyService = historyService;
    }

    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(fileService::findAll);
    }

    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() -> fileService.findById(new FileGetByIdRequest(entityId)));
    }

    @GetMapping(params = "classificationId", produces = JSON)
    public Response findByClassificationId(@RequestParam("classificationId") Long classificationId) {
        return execute(() -> fileService.getFilesByClassificationId(new FileGetByClassificationIdRequest(classificationId)));
    }

    @GetMapping(value = Endpoint.File.FREE_LANGUAGES, produces = JSON)
    public Response getFreeLanguages(@PathVariable Long documentId) {
        return execute(() -> fileService.getFreeLanguages(new FileGetFreeLanguagesByIdRequest(documentId)));
    }

    @GetMapping(value = Endpoint.File.RESOURCE)
    public ResponseEntity<?> findResource(@RequestParam(name = "clientId") Long clientId,
                                          @RequestParam(name = "documentId") Long documentId,
                                          @RequestParam(name = "languageId") Long languageId) {
        try {
            FileResource resource = fileService.load(new FileGetByIdAndLanguageIdRequest(documentId, languageId));
            historyService.save(new HistorySaveRequest(clientId, documentId, languageId));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFullFileName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource.getData());
        } catch (Exception e) {
            return ResponseEntity.of(Optional.of(handleException(e)));
        }
    }

    @PostMapping(produces = JSON)
    public Response save(@RequestParam("file") MultipartFile file,
                         @RequestParam("languageId") Long languageId,
                         @RequestParam("classificationId") Long classificationId) {
        return execute(() -> {
            try {
                FileSaveRequest rq = FileSaveRequest.builder()
                        .file(map(file))
                        .languageId(languageId)
                        .classificationId(classificationId)
                        .build();

                return fileService.save(rq);
            } catch (IOException e) {
                return handleException(e);
            }
        });
    }

    @PatchMapping(produces = JSON)
    public Response patch(@RequestParam("file") MultipartFile file,
                          @RequestParam("documentId") Long documentId,
                          @RequestParam("languageId") Long languageId) {
        return execute(() -> {
            try {
                FilePatchRequest rq = FilePatchRequest.builder()
                        .file(map(file))
                        .documentId(documentId)
                        .languageId(languageId)
                        .build();

                return fileService.patch(rq);
            } catch (IOException e) {
                return handleException(e);
            }
        });
    }

    @DeleteMapping(produces = JSON)
    public Response deleteByDocumentIdAndLanguageId(@RequestParam(name = "documentId") Long documentId,
                                                    @RequestParam(name = "languageId") Long languageId) {
        return execute(() -> fileService.deleteByDocumentIdAndLanguageId(
                new FileDeleteByIdAndLanguageIdRequest(documentId, languageId)));
    }

    @DeleteMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() -> fileService.deleteById(new FileDeleteByIdRequest(entityId)));
    }

    private CustomMultipartFile map(MultipartFile file) throws IOException {
        return CustomMultipartFile.builder()
                .withFullFileName(file.getOriginalFilename())
                .withFileName(FileUtils.getFileName(file.getOriginalFilename()))
                .withExtension(FileUtils.getFileExtension(file.getOriginalFilename()))
                .withInputStream(file.getInputStream())
                .withIsEmpty(file.isEmpty())
                .withSize(file.getSize())
                .build();
    }
}

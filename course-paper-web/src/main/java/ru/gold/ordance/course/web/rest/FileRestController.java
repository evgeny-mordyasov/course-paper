package ru.gold.ordance.course.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.internal.api.request.Response;
import ru.gold.ordance.course.internal.api.request.file.FileDeleteByUrnRequest;

public interface FileRestController {
    Response findAll();
    Response save(MultipartFile file, Long languageId, Long classificationId);
    Response patch(MultipartFile file, Long documentId, Long languageId);
    ResponseEntity<?> findResource(Long clientId, Long documentId, Long LanguageId);
    Response findById(Long entityId);
    Response findByClassificationId(Long classificationId);
    Response getFreeLanguages(Long documentId);
    Response deleteByUrn(FileDeleteByUrnRequest rq);
    Response deleteById(Long entityId);
}

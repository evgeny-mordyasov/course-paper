package ru.gold.ordance.course.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;

public interface FileRestController {
    Response findAll();
    Response save(MultipartFile file, Long languageId, Long classificationId);
    Response patch(MultipartFile file, Long documentId, Long languageId);
    ResponseEntity<?> findResourceById(Long documentId, Long LanguageId);
    Response findById(Long entityId);
    Response getFreeLanguages(Long documentId);
    Response deleteByUrn(FileDeleteByUrnRequest rq);
    Response deleteById(Long entityId);
}

package ru.gold.ordance.course.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;

public interface FileRestController {
    Response findAll();
    Response save(MultipartFile file, Long languageId, Long classificationId);
    ResponseEntity<?> findById(Long entityId);
    Response deleteByUrn(FileDeleteByUrnRequest rq);
}

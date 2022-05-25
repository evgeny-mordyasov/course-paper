package ru.gold.ordance.course.web.rest;

import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.Response;

public interface FileRestController extends AbstractRestController {
    Response findAll();
    Response save(MultipartFile file, Long languageId, Long classificationId);
}

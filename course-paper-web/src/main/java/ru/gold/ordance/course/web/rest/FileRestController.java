package ru.gold.ordance.course.web.rest;

import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.file.FileGetResponse;
import ru.gold.ordance.course.web.api.file.FileSaveResponse;

import java.io.IOException;

public interface FileRestController extends AbstractRestController {
    FileGetResponse findAll();
    FileSaveResponse save(MultipartFile file, Long languageId, Long classificationId) throws IOException;
}

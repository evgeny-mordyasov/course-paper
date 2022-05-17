package ru.gold.ordance.course.web.service.file;

import ru.gold.ordance.course.web.api.file.FileGetResponse;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.FileSaveResponse;

import java.io.IOException;

public interface FileWebService {
    FileGetResponse findAll();
    FileSaveResponse save(FileSaveRequest rq) throws IOException;
}

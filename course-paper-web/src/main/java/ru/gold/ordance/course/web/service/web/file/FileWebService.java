package ru.gold.ordance.course.web.service.web.file;

import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.service.web.WebService;

import java.io.IOException;

public interface FileWebService extends WebService {
    FileGetResponse findAll();
    FileSaveResponse save(FileSaveRequest rq) throws IOException;
    FileDeleteResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException;
}

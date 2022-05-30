package ru.gold.ordance.course.web.service.file;

import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.service.WebService;

import java.io.IOException;

public interface FileWebService extends WebService {
    FileGetResponse findAll();
    FileSaveResponse save(FileSaveRequest rq) throws IOException;
    FileDeleteByUrnResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException;
}

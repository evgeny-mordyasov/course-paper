package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.service.web.WebService;

import java.io.IOException;

public interface FileWebService extends WebService {
    FileGetListResponse findAll();
    FileGetEntityResponse findById(FileGetByIdRequest rq);
    FileSaveResponse save(FileSaveRequest rq) throws IOException;
    Resource load(FileGetByIdRequest rq) throws Exception;
    FileDeleteResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException;
}

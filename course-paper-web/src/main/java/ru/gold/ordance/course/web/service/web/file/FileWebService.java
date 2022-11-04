package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.service.web.WebService;

import java.io.IOException;

public interface FileWebService extends WebService {
    FileGetListResponse findAll();
    FileGetEntityResponse findById(FileGetByIdRequest rq);
    FileGetFreeLanguagesByIdResponse getFreeLanguages(FileGetFreeLanguagesByIdRequest rq);
    FileSaveResponse save(FileSaveRequest rq) throws IOException;
    FileSaveResponse patch(FilePatchRequest rq) throws IOException;
    Resource load(FileGetByIdAndLanguageIdRequest rq) throws Exception;
    FileDeleteResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException;
    FileDeleteResponse deleteById(FileDeleteByIdRequest rq) throws IOException;
}

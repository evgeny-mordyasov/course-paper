package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import ru.gold.ordance.course.internal.api.request.file.*;
import ru.gold.ordance.course.web.service.web.WebService;

import java.io.IOException;
import java.util.List;

public interface FileWebService extends WebService {
    FileGetListResponse findAll();
    FileGetEntityResponse findById(FileGetByIdRequest rq);
    FileGetFreeLanguagesByIdResponse getFreeLanguages(FileGetFreeLanguagesByIdRequest rq);
    FileSaveResponse save(FileSaveRequest rq) throws IOException;
    FileSaveResponse patch(FilePatchRequest rq) throws IOException;
    Resource load(FileGetByIdAndLanguageIdRequest rq) throws Exception;
    FileDeleteResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException;
    FileDeleteResponse deleteById(FileDeleteByIdRequest rq) throws IOException;
    List<String> getFilesByClassificationId(Long classificationId);
    void deleteSystemFiles(List<String> urns);
}

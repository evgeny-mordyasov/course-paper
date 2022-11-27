package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.common.exception.FileAlreadyExistsException;
import ru.gold.ordance.course.internal.api.dto.File;
import ru.gold.ordance.course.internal.api.request.file.*;
import ru.gold.ordance.course.web.service.web.file.helper.FileDatabaseHelper;
import ru.gold.ordance.course.web.service.web.file.helper.FileSystemHelper;

import java.io.IOException;
import java.util.List;

public class FileWebServiceImpl implements FileWebService {
    private final FileDatabaseHelper databaseHelper;
    private final FileSystemHelper fileSystemHelper;

    public FileWebServiceImpl(FileDatabaseHelper databaseHelper,
                              FileSystemHelper fileSystemHelper) {
        this.databaseHelper = databaseHelper;
        this.fileSystemHelper = fileSystemHelper;
    }

    @Override
    public FileGetListResponse findAll() {
        return FileGetListResponse.success(databaseHelper.findAll());
    }

    @Override
    public FileGetEntityResponse findById(FileGetByIdRequest rq) {
        return FileGetEntityResponse.success(databaseHelper.findById(rq));
    }

    @Override
    public FileGetFreeLanguagesByIdResponse getFreeLanguages(FileGetFreeLanguagesByIdRequest rq) {
        return FileGetFreeLanguagesByIdResponse.success(databaseHelper.getFreeLanguages(rq));
    }

    @Override
    @Transactional
    public FileSaveResponse save(FileSaveRequest rq) throws IOException {
        File stored = fileSystemHelper.getFile(rq.getFile());

        WebFile webFile = databaseHelper.save(stored, rq.getClassificationId(), rq.getLanguageId());
        fileSystemHelper.save(rq.getFile(), stored);

        return FileSaveResponse.success(webFile);
    }

    @Override
    @Transactional
    public FileSaveResponse patch(FilePatchRequest rq) throws IOException {
        if (databaseHelper.isExistsLanguageForDocument(rq.getLanguageId(), rq.getDocumentId())) {
            throw new FileAlreadyExistsException("The language of the file already exists.");
        }

        File stored = fileSystemHelper.getFile(rq.getFile());

        WebFile webFile = databaseHelper.patch(rq, stored.getUrn());
        fileSystemHelper.save(rq.getFile(), stored);

        return FileSaveResponse.success(webFile);
    }

    @Override
    public Resource load(FileGetByIdAndLanguageIdRequest rq) throws Exception {
        String urn = databaseHelper.getUrn(rq.getDocumentId(), rq.getLanguageId());

        return fileSystemHelper.getResource(urn);
    }

    @Override
    @Transactional
    public FileDeleteResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException {
        databaseHelper.deleteByUrn(rq);
        fileSystemHelper.deleteByUrn(rq.getUrn());

        return FileDeleteResponse.success();
    }

    @Override
    public FileDeleteResponse deleteById(FileDeleteByIdRequest rq) throws IOException {
        List<String> urns = databaseHelper.deleteById(rq);
        fileSystemHelper.deleteByUrn(urns);

        return FileDeleteResponse.success();
    }

    @Override
    public List<String> getFilesByClassificationId(Long classificationId) {
        return databaseHelper.getUrns(classificationId);
    }

    @Override
    public void deleteSystemFiles(List<String> urns) {
        try {
            fileSystemHelper.deleteByUrn(urns);
        } catch (Exception ignored) {}
    }
}

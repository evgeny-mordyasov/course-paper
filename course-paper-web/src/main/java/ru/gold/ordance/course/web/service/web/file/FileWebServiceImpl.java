package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.service.web.file.helper.FileDatabaseHelper;
import ru.gold.ordance.course.web.service.web.file.helper.FileSystemHelper;

import java.io.IOException;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.*;

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
        return databaseHelper.findAll();
    }

    @Override
    public FileGetEntityResponse findById(FileGetByIdRequest rq) {
        return databaseHelper.findById(rq);
    }

    @Override
    @Transactional
    public FileSaveResponse save(FileSaveRequest rq) throws IOException {
        String language = getLanguageById(rq.getLanguageId()).getName();
        String classification = getClassificationById(rq.getClassificationId()).getName();

        String urn = fileSystemHelper.save(rq.getFile(), classification, language);
        return databaseHelper.save(rq, urn);
    }

    @Override
    @Transactional
    public FileSaveResponse patch(FilePatchRequest rq) throws IOException {
        String language = getLanguageById(rq.getLanguageId()).getName();
        String classification = getDocumentById(rq.getDocumentId()).getClassification().getName();

        String urn = fileSystemHelper.save(rq.getFile(), classification, language);
        return databaseHelper.patch(rq, urn);
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
}

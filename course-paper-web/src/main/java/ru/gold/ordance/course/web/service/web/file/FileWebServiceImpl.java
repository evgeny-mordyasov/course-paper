package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.dto.File;
import ru.gold.ordance.course.web.exception.FileAlreadyExistsException;
import ru.gold.ordance.course.web.service.web.file.helper.FileDatabaseHelper;
import ru.gold.ordance.course.web.service.web.file.helper.FileSystemHelper;

import java.io.IOException;


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
        File stored = fileSystemHelper.save(rq.getFile());
        return databaseHelper.save(stored, rq.getClassificationId(), rq.getLanguageId());
    }

    @Override
    @Transactional
    public FileSaveResponse patch(FilePatchRequest rq) throws IOException {
        if (databaseHelper.isExistsLanguageForDocument(rq.getLanguageId(), rq.getDocumentId())) {
            throw new FileAlreadyExistsException("The language of the file already exists.");
        }

        File stored = fileSystemHelper.save(rq.getFile());
        return databaseHelper.patch(rq, stored.getUrn());
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

package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.service.web.file.helper.FileDatabaseHelper;
import ru.gold.ordance.course.web.service.web.file.helper.FileSystemHelper;

import java.io.IOException;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.*;
import static ru.gold.ordance.course.common.utils.FileUtils.createUrn;

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
        setUrn(rq);

        fileSystemHelper.save(rq.getFile(), rq.getUrn());
        return databaseHelper.save(rq);
    }

    @Override
    public FileSaveResponse patch(FilePatchRequest rq) throws IOException {
        setUrn(rq);

        fileSystemHelper.save(rq.getFile(), rq.getUrn());
        return databaseHelper.patch(rq);
    }

    @Override
    public Resource load(FileGetByIdAndLanguageIdRequest rq) throws Exception {
        FileGetEntityResponse rs = databaseHelper.findByDocumentIdAndLanguageId(rq.getDocumentId(), rq.getLanguageId());

        return fileSystemHelper.getResource(rs.getFile().getLanguages().get(0).getUrn());
    }

    private void setUrn(FileSaveRequest rq) {
       rq.setUrn(createUrn(
               getClassificationById(rq.getClassificationId()).getName(),
               getLanguageById(rq.getLanguageId()).getName(),
               rq.getFile().getOriginalFilename()));
    }

    private void setUrn(FilePatchRequest rq) {
        rq.setUrn(createUrn(
                getDocumentById(rq.getDocumentId()).getClassification().getName(),
                getLanguageById(rq.getLanguageId()).getName(),
                rq.getFile().getOriginalFilename()));
    }

    @Override
    @Transactional
    public FileDeleteResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException {
        databaseHelper.deleteByUrn(rq);
        fileSystemHelper.deleteByUrn(rq);

        return FileDeleteResponse.success();
    }
}

package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.service.web.file.helper.FileDatabaseHelper;
import ru.gold.ordance.course.web.service.web.file.helper.FileSystemHelper;

import java.io.IOException;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getClassificationById;
import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getLanguageById;
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

        fileSystemHelper.save(rq);
        return databaseHelper.save(rq);
    }

    @Override
    public Resource load(FileGetByIdRequest rq) throws Exception {
        FileGetEntityResponse rs = databaseHelper.findById(rq);

        return fileSystemHelper.getResource(rs.getFile().getUrn());
    }

    private void setUrn(FileSaveRequest rq) {
       rq.setUrn(createUrn(
               getClassificationById(rq.getClassificationId()).getName(),
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

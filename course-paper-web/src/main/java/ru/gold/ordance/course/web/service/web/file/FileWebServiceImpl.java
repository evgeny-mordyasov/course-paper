package ru.gold.ordance.course.web.service.web.file;

import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.exception.FileNotFoundException;
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
    @Transactional
    public FileSaveResponse save(FileSaveRequest rq) throws IOException {
        setUrn(rq);

        FileSaveResponse rs = databaseHelper.save(rq);
        fileSystemHelper.save(rq);

        return rs;
    }

    @Override
    public Resource load(FileGetByIdRequest rq) throws Exception {
        FileGetEntityResponse rs = databaseHelper.findById(rq);

        if (rs.getFile() != null) {
            return fileSystemHelper.getResource(rs.getFile().getUrn());
        }

        throw new FileNotFoundException("The '" + rs.getFile().getUrn() + "' not found.");
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

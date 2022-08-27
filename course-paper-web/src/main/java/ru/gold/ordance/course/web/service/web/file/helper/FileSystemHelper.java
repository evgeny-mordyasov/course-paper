package ru.gold.ordance.course.web.service.web.file.helper;

import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.exception.FileAlreadyExistsException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.gold.ordance.course.common.utils.FileUtils.urnWithoutFileName;

public class FileSystemHelper {
    private final String storagePath;

    public FileSystemHelper(String storagePath) {
        this.storagePath = storagePath;
    }

    public void save(FileSaveRequest rq) throws IOException {
        throwExceptionIfFileExistsBy(rq);

        moveFileTo(rq.getUrn(), rq.getFile());
    }

    private void throwExceptionIfFileExistsBy(FileSaveRequest rq) {
        if (Files.isRegularFile(Paths.get(storagePath + rq.getUrn()))) {
            throw new FileAlreadyExistsException("The file '" + rq.getUrn() + "' already exists.");
        }
    }

    public void moveFileTo(String urn, MultipartFile file) throws IOException {
        creatingDirectoriesFor(urn);
        file.transferTo(new File(storagePath + urn));
    }

    private void creatingDirectoriesFor(String urn) throws IOException {
        Path fullPath = Path.of(storagePath + urnWithoutFileName(urn));
        Files.createDirectories(fullPath);
    }

    public void deleteByUrn(FileDeleteByUrnRequest rq) throws IOException {
        Files.deleteIfExists(Path.of(storagePath + rq.getUrn()));
    }
}

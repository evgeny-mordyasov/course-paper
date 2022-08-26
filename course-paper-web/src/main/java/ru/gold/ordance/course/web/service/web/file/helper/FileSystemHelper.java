package ru.gold.ordance.course.web.service.web.file.helper;

import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemHelper {
    private final String storagePath;

    public FileSystemHelper(String storagePath) {
        this.storagePath = storagePath;
    }

    public void save(FileSaveRequest rq) throws IOException {
        moveFileTo(rq.getUrn(), rq.getFile());
    }

    public void moveFileTo(String urn, MultipartFile file) throws IOException {
        creatingDirectoriesFor(urn);
        file.transferTo(new File(storagePath + urn));
    }

    private void creatingDirectoriesFor(String urn) throws IOException {
        try {
            Path fullPath = Path.of(storagePath + urn);
            Files.createDirectories(fullPath);
        } catch (FileAlreadyExistsException e) {
            throw new ru.gold.ordance.course.web.exception.FileAlreadyExistsException("File " + e.getMessage() + " already exists.");
        }
    }

    public void deleteByUrn(FileDeleteByUrnRequest rq) throws IOException {
        Files.deleteIfExists(Path.of(storagePath + rq.getUrn()));
    }
}

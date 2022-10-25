package ru.gold.ordance.course.web.service.web.file.helper;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;
import ru.gold.ordance.course.web.exception.FileAlreadyExistsException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.gold.ordance.course.common.utils.FileUtils.urnWithoutFileName;

public class FileSystemHelper {
    private final String storagePath;

    public FileSystemHelper(String storagePath) {
        this.storagePath = storagePath;
    }

    public void save(MultipartFile file, String urn) throws IOException {
        throwExceptionIfFileExistsBy(urn);

        moveFileTo(urn, file);
    }

    private void throwExceptionIfFileExistsBy(String urn) {
        if (Files.isRegularFile(Paths.get(storagePath + urn))) {
            throw new FileAlreadyExistsException("The file '" + urn + "' already exists.");
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

    public Resource getResource(String urn) throws MalformedURLException {
        Path file = Paths.get(storagePath + urn);
        return new UrlResource(file.toUri());
    }
}

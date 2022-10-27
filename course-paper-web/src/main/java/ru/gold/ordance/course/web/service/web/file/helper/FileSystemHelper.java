package ru.gold.ordance.course.web.service.web.file.helper;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.exception.FileAlreadyExistsException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ru.gold.ordance.course.common.utils.FileUtils.createUrn;
import static ru.gold.ordance.course.common.utils.FileUtils.urnWithoutFileName;

public class FileSystemHelper {
    private final String storagePath;

    public FileSystemHelper(String storagePath) {
        this.storagePath = storagePath;
    }

    public String save(MultipartFile file, String classification, String language) throws IOException {
        String urn = storagePath + createUrn(classification, language, file.getOriginalFilename());
        throwExceptionIfFileExistsBy(urn);

        moveFileTo(urn, file);

        return urn;
    }

    private void throwExceptionIfFileExistsBy(String urn) {
        if (Files.isRegularFile(Paths.get(urn))) {
            throw new FileAlreadyExistsException("The file '" + urn + "' already exists.");
        }
    }

    public void moveFileTo(String urn, MultipartFile file) throws IOException {
        creatingDirectoriesFor(urn);
        file.transferTo(new File(urn));
    }

    private void creatingDirectoriesFor(String urn) throws IOException {
        Path fullPath = Path.of(urnWithoutFileName(urn));
        Files.createDirectories(fullPath);
    }

    public void deleteByUrn(String urn) throws IOException {
        Files.deleteIfExists(Path.of(urn));
    }

    public Resource getResource(String urn) throws MalformedURLException {
        return new UrlResource(Paths.get(urn).toUri());
    }
}

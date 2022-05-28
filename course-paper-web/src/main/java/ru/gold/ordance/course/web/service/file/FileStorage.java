package ru.gold.ordance.course.web.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.base.utils.PersistenceHelper;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public final class FileStorage {
    private final PersistenceHelper persistence;

    private final String storagePath;

    public FileStorage(PersistenceHelper persistence,
                       @Value("${spring.servlet.multipart.location}") String storagePath) {
        this.persistence = persistence;
        this.storagePath = storagePath;
    }

    public String getURN(FileSaveRequest rq) {
        return formURN(
                persistence.getClassificationNameById(rq.getClassificationId()),
                persistence.getLanguageNameById(rq.getLanguageId()),
                rq.getFile().getOriginalFilename());
    }

    private String formURN(String classificationName, String languageName, String fullFileName) {
        return String.format("/%s/%s/%s", classificationName, languageName, fullFileName);
    }

    public void moveFileTo(String URN, MultipartFile file) throws IOException {
        creatingDirectoriesFor(URN);
        file.transferTo(new File(storagePath + URN));
    }

    private void creatingDirectoriesFor(String URN) throws IOException {
        Path fullPath = Path.of(storagePath + urnWithoutFileName(URN));
        Files.createDirectories(fullPath);
    }

    private String urnWithoutFileName(String URN) {
        return URN.substring(0, URN.lastIndexOf("/"));
    }
}

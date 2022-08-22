package ru.gold.ordance.course.web.service.web.file;

import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getClassificationNameById;
import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getLanguageNameById;

public final class FileStorage {
    private final String storagePath;

    public FileStorage(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getURN(FileSaveRequest rq) {
        return formURN(
                getClassificationNameById(rq.getClassificationId()),
                getLanguageNameById(rq.getLanguageId()),
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

    public void deleteFileByUrn(String URN) throws IOException {
        Files.deleteIfExists(Path.of(storagePath + URN));
    }
}

package ru.gold.ordance.course.web.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.utils.StorageHelper;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public final class FileStorage {
    private final StorageHelper storage;

    private final String path;

    public FileStorage(StorageHelper storage,
                       @Value("${spring.servlet.multipart.location}") String path) {
        this.storage = storage;
        this.path = path;
    }

    public String getUrn(FileSaveRequest rq) {
        final String classificationName = storage.findById(Classification.class, rq.getClassificationId()).getName();
        final String languageName = storage.findById(Language.class, rq.getLanguageId()).getName();


        return String.format("/%s/%s/%s", classificationName, languageName, rq.getFile().getOriginalFilename());
    }

    public void moveFileTo(MultipartFile file, String urn) throws IOException {
        Files.createDirectories(Path.of(String.format("%s/%s", path, urn.substring(0, urn.lastIndexOf("/")))));
        file.transferTo(new File(path + urn));
    }
}

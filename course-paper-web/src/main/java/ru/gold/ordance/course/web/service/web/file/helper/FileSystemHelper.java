package ru.gold.ordance.course.web.service.web.file.helper;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.dto.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.gold.ordance.course.common.utils.FileUtils.*;

public class FileSystemHelper {
    private final String storagePath;

    public FileSystemHelper(String storagePath) {
        this.storagePath = storagePath;
    }

    public File getFile(MultipartFile file) {
        String fileName = randomFileName();
        String extension = getFileExtension(file.getOriginalFilename());
        String systemFullFileName = fileName + "." + extension;

        return File.builder()
                .withUrn(storagePath + systemFullFileName)
                .withFullFileName(file.getOriginalFilename())
                .withFileName(getFileName(file.getOriginalFilename()))
                .withExtension(extension)
                .build();
    }

    public void save(MultipartFile file, File stored) throws IOException {
        Files.copy(file.getInputStream(), Paths.get(stored.getUrn()));
    }

    public void deleteByUrn(String urn) throws IOException {
        Files.deleteIfExists(Path.of(urn));
    }

    public void deleteByUrn(List<String> urns) throws IOException {
        for(String urn : urns) {
            deleteByUrn(urn);
        }
    }

    public Resource getResource(String urn) throws MalformedURLException {
        return new UrlResource(Paths.get(urn).toUri());
    }
}

package ru.gold.ordance.course.web.service.web.file;

import java.util.Arrays;

public enum FileExtension {
    DOC(".doc"),
    DOCX(".docx"),
    RTF(".rtf"),
    TXT(".txt");

    private final String extension;

    public static boolean isFromWhitelist(String extension) {
        return Arrays.stream(values())
                .map(ext -> ext.extension)
                .anyMatch(ext -> ext.equals(extension));
    }

    FileExtension(String extension) {
        this.extension = extension;
    }
}

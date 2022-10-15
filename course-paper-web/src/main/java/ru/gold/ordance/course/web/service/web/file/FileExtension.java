package ru.gold.ordance.course.web.service.web.file;

import java.util.Arrays;

public enum FileExtension {
    DOC,
    DOCX,
    RTF,
    TXT,
    PDF;

    public static boolean isFromWhitelist(String extension) {
        return Arrays.stream(values())
                .map(Enum::name)
                .anyMatch(ext -> ext.equalsIgnoreCase(extension));
    }
}

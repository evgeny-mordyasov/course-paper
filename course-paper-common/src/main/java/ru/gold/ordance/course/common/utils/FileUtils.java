package ru.gold.ordance.course.common.utils;

import java.util.UUID;

public final class FileUtils {
    private FileUtils() {
    }

    public static String getFileExtension(String fullFileName) {
        return fullFileName.substring(fullFileName.indexOf(".") + 1);
    }

    public static String getFileName(String fullFileName) {
        return fullFileName.substring(0, fullFileName.indexOf("."));
    }

    public static String randomFileName() {
        return UUID.randomUUID().toString();
    }
}

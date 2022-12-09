package ru.gold.ordance.course.common.utils;

public final class FileUtils {
    private FileUtils() {
    }

    public static String getFileExtension(String fullFileName) {
        return fullFileName.substring(fullFileName.indexOf(".") + 1);
    }

    public static String getFileName(String fullFileName) {
        return fullFileName.substring(0, fullFileName.indexOf("."));
    }
}

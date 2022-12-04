package ru.gold.ordance.course.internal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;

@Builder(setterPrefix = "with")
@Getter
@ToString
public class CustomMultipartFile {
    private final String fullFileName;
    private final String fileName;
    private final String extension;
    private final InputStream inputStream;
    private final boolean isEmpty;
    private final long size;
}

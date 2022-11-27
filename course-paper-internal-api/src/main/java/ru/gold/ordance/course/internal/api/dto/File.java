package ru.gold.ordance.course.internal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder(setterPrefix = "with")
@Getter
@ToString
public class File {
    private final String urn;
    private final String fullFileName;
    private final String fileName;
    private final String extension;
}

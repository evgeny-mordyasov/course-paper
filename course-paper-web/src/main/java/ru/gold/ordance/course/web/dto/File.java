package ru.gold.ordance.course.web.dto;

import lombok.*;

@Builder(setterPrefix = "with")
@Getter
@ToString
public class File {
    private final String urn;
    private final String fullFileName;
    private final String fileName;
    private final String extension;
}

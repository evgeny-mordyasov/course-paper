package ru.gold.ordance.course.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.core.io.ByteArrayResource;

@Builder(setterPrefix = "with")
@Getter
@ToString
public class FileResource {
    private final ByteArrayResource data;
    private final String fullFileName;
}

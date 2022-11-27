package ru.gold.ordance.course.internal.api.request.file;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder(setterPrefix = "with", toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final WebDocument document;
    private final List<WebDocumentLanguage> languages;
}

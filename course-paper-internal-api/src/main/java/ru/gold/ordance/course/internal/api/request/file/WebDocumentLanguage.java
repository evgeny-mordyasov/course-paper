package ru.gold.ordance.course.internal.api.request.file;

import lombok.*;
import ru.gold.ordance.course.internal.api.request.language.WebLanguage;

import java.io.Serializable;

@Builder(setterPrefix = "with", toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebDocumentLanguage implements Serializable {
    private static final long serialVersionUID = 1L;

    private final WebLanguage language;
    private final String urn;
    private final String url;
}

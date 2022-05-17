package ru.gold.ordance.course.web.api.file;

import lombok.*;

import java.io.Serializable;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long documentId;

    private final Long languageId;

    private final Long classificationId;

    private final String urn;
}

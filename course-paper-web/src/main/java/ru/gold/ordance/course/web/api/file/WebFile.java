package ru.gold.ordance.course.web.api.file;

import lombok.*;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;

import java.io.Serializable;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Document document;

    private final Language language;

    private final String urn;
}

package ru.gold.ordance.course.web.api.language;

import lombok.*;

import java.io.Serializable;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebLanguage implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;
}

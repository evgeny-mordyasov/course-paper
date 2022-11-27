package ru.gold.ordance.course.internal.api.request.classification;

import lombok.*;

import java.io.Serializable;

@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebClassification implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final String name;
}

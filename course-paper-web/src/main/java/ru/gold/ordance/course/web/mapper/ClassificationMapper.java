package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.domain.classification.request.ClassificationSaveRequest;
import ru.gold.ordance.course.internal.api.domain.classification.request.ClassificationUpdateRequest;
import ru.gold.ordance.course.internal.api.domain.classification.model.WebClassification;
import ru.gold.ordance.course.persistence.entity.impl.Classification;

public final class ClassificationMapper {
    private static final ClassificationMapper INSTANCE = new ClassificationMapper();

    private ClassificationMapper() {
    }

    public static ClassificationMapper instance() {
        return INSTANCE;
    }

    public Classification toClassification(ClassificationSaveRequest rq) {
        return Classification.builder()
                .withName(rq.getName())
                .build();
    }

    public Classification toClassification(ClassificationUpdateRequest rq) {
        return Classification.builder()
                .withEntityId(rq.getEntityId())
                .withName(rq.getName())
                .build();
    }

    public WebClassification fromClassification(Classification classification) {
        return WebClassification.builder()
                .withEntityId(classification.getEntityId())
                .withName(classification.getName())
                .build();
    }
}

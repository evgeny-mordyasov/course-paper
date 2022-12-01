package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.internal.api.domain.classification.request.ClassificationSaveRequest;
import ru.gold.ordance.course.internal.api.domain.classification.request.ClassificationUpdateRequest;
import ru.gold.ordance.course.internal.api.domain.classification.model.WebClassification;
import ru.gold.ordance.course.persistence.entity.impl.Classification;
import ru.gold.ordance.course.web.mapper.ClassificationMapper;

public class ClassificationMapperImpl implements ClassificationMapper {

    @Override
    public Classification toClassification(ClassificationSaveRequest rq) {
        return Classification.builder()
                .withName(rq.getName())
                .build();
    }

    @Override
    public Classification toClassification(ClassificationUpdateRequest rq) {
        return Classification.builder()
                .withEntityId(rq.getEntityId())
                .withName(rq.getName())
                .build();
    }

    @Override
    public WebClassification fromClassification(Classification classification) {
        return WebClassification.builder()
                .withEntityId(classification.getEntityId())
                .withName(classification.getName())
                .build();
    }
}

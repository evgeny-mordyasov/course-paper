package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.web.api.classification.ClassificationSaveRequest;
import ru.gold.ordance.course.web.api.classification.ClassificationUpdateRequest;
import ru.gold.ordance.course.web.api.classification.WebClassification;
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
                .withId(rq.getEntityId())
                .withName(rq.getName())
                .build();
    }

    @Override
    public WebClassification fromClassification(Classification classification) {
        return WebClassification.builder()
                .withEntityId(classification.getId())
                .withName(classification.getName())
                .build();
    }
}

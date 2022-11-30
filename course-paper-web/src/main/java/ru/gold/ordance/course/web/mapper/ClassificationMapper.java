package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.request.classification.ClassificationSaveRequest;
import ru.gold.ordance.course.internal.api.request.classification.ClassificationUpdateRequest;
import ru.gold.ordance.course.internal.api.request.classification.WebClassification;
import ru.gold.ordance.course.persistence.entity.impl.Classification;

public interface ClassificationMapper {
    Classification toClassification(ClassificationSaveRequest rq);

    Classification toClassification(ClassificationUpdateRequest rq);

    WebClassification fromClassification(Classification classification);
}

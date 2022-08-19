package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.web.api.classification.ClassificationSaveRequest;
import ru.gold.ordance.course.web.api.classification.ClassificationUpdateRequest;
import ru.gold.ordance.course.web.api.classification.WebClassification;

public interface ClassificationMapper {
    Classification toClassification(ClassificationSaveRequest rq);

    Classification toClassification(ClassificationUpdateRequest rq);

    WebClassification fromClassification(Classification classification);
}

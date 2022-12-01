package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.classification.request.ClassificationSaveRequest;
import ru.gold.ordance.course.internal.api.domain.classification.request.ClassificationUpdateRequest;

public interface ClassificationRestController {
    Response findAll();
    Response findById(Long entityId);
    Response findByName(String name);
    Response save(ClassificationSaveRequest rq);
    Response update(ClassificationUpdateRequest rq);
    Response deleteById(Long entityId);
}

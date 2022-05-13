package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.classification.*;

public interface ClassificationRestController extends AbstractRestController {
    ClassificationGetResponse findAll();
    ClassificationGetResponse findById(Long entityId);
    ClassificationGetResponse findByName(String name);
    ClassificationSaveResponse save(ClassificationSaveRequest rq);
    ClassificationUpdateResponse update(ClassificationUpdateRequest rq);
    ClassificationDeleteByIdResponse deleteById(Long entityId);
}

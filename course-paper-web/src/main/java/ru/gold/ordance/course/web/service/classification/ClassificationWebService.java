package ru.gold.ordance.course.web.service.classification;

import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.service.WebService;

public interface ClassificationWebService extends WebService {
    ClassificationGetResponse findAll();
    ClassificationGetResponse findById(ClassificationGetByIdRequest rq);
    ClassificationGetResponse findByName(ClassificationGetByNameRequest rq);
    ClassificationSaveResponse save(ClassificationSaveRequest rq);
    ClassificationUpdateResponse update(ClassificationUpdateRequest rq);
    ClassificationDeleteByIdResponse deleteById(ClassificationDeleteByIdRequest rq);
}

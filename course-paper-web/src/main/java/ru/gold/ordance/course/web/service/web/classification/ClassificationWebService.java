package ru.gold.ordance.course.web.service.web.classification;

import ru.gold.ordance.course.internal.api.request.Response;
import ru.gold.ordance.course.internal.api.request.classification.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface ClassificationWebService extends WebService {
    ClassificationGetListResponse findAll();
    Response findById(ClassificationGetByIdRequest rq);
    Response findByName(ClassificationGetByNameRequest rq);
    ClassificationSaveResponse save(ClassificationSaveRequest rq);
    ClassificationUpdateResponse update(ClassificationUpdateRequest rq);
    ClassificationDeleteResponse deleteById(ClassificationDeleteByIdRequest rq);
}

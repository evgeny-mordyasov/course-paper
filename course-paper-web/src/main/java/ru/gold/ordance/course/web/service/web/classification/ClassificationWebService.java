package ru.gold.ordance.course.web.service.web.classification;

import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.classification.request.*;
import ru.gold.ordance.course.internal.api.domain.classification.response.ClassificationDeleteResponse;
import ru.gold.ordance.course.internal.api.domain.classification.response.ClassificationGetListResponse;
import ru.gold.ordance.course.internal.api.domain.classification.response.ClassificationSaveResponse;
import ru.gold.ordance.course.internal.api.domain.classification.response.ClassificationUpdateResponse;
import ru.gold.ordance.course.web.service.web.WebService;

public interface ClassificationWebService extends WebService {
    ClassificationGetListResponse findAll();
    Response findById(ClassificationGetByIdRequest rq);
    Response findByName(ClassificationGetByNameRequest rq);
    ClassificationSaveResponse save(ClassificationSaveRequest rq);
    ClassificationUpdateResponse update(ClassificationUpdateRequest rq);
    ClassificationDeleteResponse deleteById(ClassificationDeleteByIdRequest rq);
}

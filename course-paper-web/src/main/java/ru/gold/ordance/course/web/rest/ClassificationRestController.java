package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.classification.*;

public interface ClassificationRestController {
    Response findAll();
    Response findById(Long entityId);
    Response findByName(String name);
    Response save(ClassificationSaveRequest rq);
    Response update(ClassificationUpdateRequest rq);
    Response deleteById(Long entityId);
}

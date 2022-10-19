package ru.gold.ordance.course.web.rest;

import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.history.HistorySaveRequest;

public interface HistoryRestController {
    Response findAll();
    Response save(HistorySaveRequest rq);
}

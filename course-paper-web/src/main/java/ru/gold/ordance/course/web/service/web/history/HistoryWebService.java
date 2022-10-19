package ru.gold.ordance.course.web.service.web.history;

import ru.gold.ordance.course.web.api.history.HistoryGetListResponse;
import ru.gold.ordance.course.web.api.history.HistorySaveRequest;
import ru.gold.ordance.course.web.api.history.HistorySaveResponse;
import ru.gold.ordance.course.web.service.web.WebService;

public interface HistoryWebService extends WebService {
    HistoryGetListResponse findAll();
    HistorySaveResponse save(HistorySaveRequest rq);
}

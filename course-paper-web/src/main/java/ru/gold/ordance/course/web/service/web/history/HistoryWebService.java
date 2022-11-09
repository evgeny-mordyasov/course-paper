package ru.gold.ordance.course.web.service.web.history;

import ru.gold.ordance.course.web.api.history.*;
import ru.gold.ordance.course.web.service.web.WebService;

public interface HistoryWebService extends WebService {
    HistoryGetListResponse findAll();
    HistorySaveResponse save(HistorySaveRequest rq);
    HistoryGetNumberOfDownloadsResponse getNumberOfDownloads(HistoryGetNumberOfDownloadsRequest rq);
}

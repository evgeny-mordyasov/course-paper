package ru.gold.ordance.course.web.service.web.history;

import ru.gold.ordance.course.internal.api.domain.history.request.HistoryGetNumberOfDownloadsRequest;
import ru.gold.ordance.course.internal.api.domain.history.request.HistorySaveRequest;
import ru.gold.ordance.course.internal.api.domain.history.response.HistoryGetListResponse;
import ru.gold.ordance.course.internal.api.domain.history.response.HistoryGetNumberOfDownloadsResponse;
import ru.gold.ordance.course.internal.api.domain.history.response.HistorySaveResponse;
import ru.gold.ordance.course.web.service.web.WebService;

public interface HistoryWebService extends WebService {
    HistoryGetListResponse findAll();
    HistorySaveResponse save(HistorySaveRequest rq);
    HistoryGetNumberOfDownloadsResponse getNumberOfDownloads(HistoryGetNumberOfDownloadsRequest rq);
}

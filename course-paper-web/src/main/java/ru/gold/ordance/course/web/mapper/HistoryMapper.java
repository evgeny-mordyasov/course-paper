package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.request.history.HistorySaveRequest;
import ru.gold.ordance.course.internal.api.request.history.WebHistory;
import ru.gold.ordance.course.persistence.entity.History;

public interface HistoryMapper {
    History toHistory(HistorySaveRequest rq);

    WebHistory fromHistory(History history);
}

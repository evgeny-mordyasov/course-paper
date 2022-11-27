package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.persistence.entity.History;
import ru.gold.ordance.course.web.api.history.HistorySaveRequest;
import ru.gold.ordance.course.web.api.history.WebHistory;

public interface HistoryMapper {
    History toHistory(HistorySaveRequest rq);

    WebHistory fromHistory(History history);
}

package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.domain.history.request.HistorySaveRequest;
import ru.gold.ordance.course.internal.api.domain.history.model.WebHistory;
import ru.gold.ordance.course.persistence.entity.impl.History;

public interface HistoryMapper {
    History toHistory(HistorySaveRequest rq);

    WebHistory fromHistory(History history);
}

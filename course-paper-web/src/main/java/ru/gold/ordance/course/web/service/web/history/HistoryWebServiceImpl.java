package ru.gold.ordance.course.web.service.web.history;

import ru.gold.ordance.course.base.entity.History;
import ru.gold.ordance.course.base.service.core.sub.HistoryService;
import ru.gold.ordance.course.web.api.history.HistoryGetListResponse;
import ru.gold.ordance.course.web.api.history.HistorySaveRequest;
import ru.gold.ordance.course.web.api.history.HistorySaveResponse;
import ru.gold.ordance.course.web.mapper.HistoryMapper;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryWebServiceImpl implements HistoryWebService {
    private final HistoryService service;
    private final HistoryMapper mapper;

    public HistoryWebServiceImpl(HistoryService service, HistoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }


    @Override
    public HistoryGetListResponse findAll() {
        List<History> historyList = service.findAll();

        return HistoryGetListResponse.success(
                historyList.stream()
                        .map(mapper::fromHistory)
                        .collect(Collectors.toList()));
    }

    @Override
    public HistorySaveResponse save(HistorySaveRequest rq) {
        History history = service.save(mapper.toHistory(rq));

        return HistorySaveResponse.success(mapper.fromHistory(history));
    }
}

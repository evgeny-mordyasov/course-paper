package ru.gold.ordance.course.web.service.web.history;

import ru.gold.ordance.course.internal.api.request.history.*;
import ru.gold.ordance.course.persistence.entity.impl.History;
import ru.gold.ordance.course.base.service.core.sub.HistoryService;
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

    @Override
    public HistoryGetNumberOfDownloadsResponse getNumberOfDownloads(HistoryGetNumberOfDownloadsRequest rq) {
        List<History> histories = service.findByDocumentId(rq.getDocumentId());
        int downloads = histories.stream()
                .map(h -> h.getClient().getEntityId())
                .collect(Collectors.toSet())
                .size();

        return HistoryGetNumberOfDownloadsResponse.success(downloads);
    }
}

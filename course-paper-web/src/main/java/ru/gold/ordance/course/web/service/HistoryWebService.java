package ru.gold.ordance.course.web.service;

import ru.gold.ordance.course.base.service.core.HistoryService;
import ru.gold.ordance.course.internal.api.domain.history.request.HistoryGetNumberOfDownloadsRequest;
import ru.gold.ordance.course.internal.api.domain.history.request.HistorySaveRequest;
import ru.gold.ordance.course.internal.api.domain.history.response.HistoryGetListResponse;
import ru.gold.ordance.course.internal.api.domain.history.response.HistoryGetNumberOfDownloadsResponse;
import ru.gold.ordance.course.internal.api.domain.history.response.HistorySaveResponse;
import ru.gold.ordance.course.persistence.entity.impl.History;
import ru.gold.ordance.course.web.mapper.HistoryMapper;

import java.util.List;
import java.util.stream.Collectors;

public class HistoryWebService implements WebService {
    private final HistoryService service;
    private final HistoryMapper mapper;

    public HistoryWebService(HistoryService service) {
        this.service = service;
        this.mapper = HistoryMapper.instance();
    }

    public HistoryGetListResponse findAll() {
        List<History> historyList = service.findAll();

        return HistoryGetListResponse.success(
                historyList.stream()
                        .map(mapper::fromHistory)
                        .collect(Collectors.toList()));
    }

    public HistorySaveResponse save(HistorySaveRequest rq) {
        History history = service.save(mapper.toHistory(rq));

        return HistorySaveResponse.success(mapper.fromHistory(history));
    }

    public HistoryGetNumberOfDownloadsResponse getNumberOfDownloads(HistoryGetNumberOfDownloadsRequest rq) {
        List<History> histories = service.findByDocumentId(rq.getDocumentId());
        int downloads = histories.stream()
                .map(h -> h.getClient().getEntityId())
                .collect(Collectors.toSet())
                .size();

        return HistoryGetNumberOfDownloadsResponse.success(downloads);
    }
}

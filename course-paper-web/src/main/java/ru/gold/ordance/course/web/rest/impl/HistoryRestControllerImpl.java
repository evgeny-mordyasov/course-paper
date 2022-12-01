package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.history.request.HistoryGetNumberOfDownloadsRequest;
import ru.gold.ordance.course.web.service.web.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.rest.HistoryRestController;
import ru.gold.ordance.course.web.service.web.history.HistoryWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.History.BASE_URL)
public class HistoryRestControllerImpl implements HistoryRestController {
    private final HistoryWebService service;

    public HistoryRestControllerImpl(HistoryWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(service::findAll);
    }

    @Override
    @GetMapping(value = Endpoint.History.DOCUMENT_ID_VARIABLE, produces = JSON)
    public Response getNumberOfDownloadsByDocumentId(@PathVariable Long documentId) {
        return execute(() -> service.getNumberOfDownloads(new HistoryGetNumberOfDownloadsRequest(documentId)));
    }
}

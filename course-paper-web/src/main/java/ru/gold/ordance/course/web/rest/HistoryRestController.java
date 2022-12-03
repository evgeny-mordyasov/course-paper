package ru.gold.ordance.course.web.rest;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.history.request.HistoryGetNumberOfDownloadsRequest;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.service.HistoryWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.History.BASE_URL)
public class HistoryRestController {
    private final HistoryWebService service;

    public HistoryRestController(HistoryWebService service) {
        this.service = service;
    }

    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(service::findAll);
    }

    @GetMapping(value = Endpoint.History.DOCUMENT_ID_VARIABLE, produces = JSON)
    public Response getNumberOfDownloadsByDocumentId(@PathVariable Long documentId) {
        return execute(() -> service.getNumberOfDownloads(new HistoryGetNumberOfDownloadsRequest(documentId)));
    }
}

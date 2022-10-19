package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.history.HistorySaveRequest;
import ru.gold.ordance.course.web.rest.HistoryRestController;
import ru.gold.ordance.course.web.service.web.history.HistoryWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping("/api/v1/history")
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
    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody HistorySaveRequest rq) {
        return execute(() -> service.save(rq));
    }
}

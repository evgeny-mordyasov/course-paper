package ru.gold.ordance.course.web.rest;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.classification.request.*;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.service.ClassificationWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.Classification.BASE_URL)
public class ClassificationRestController {
    private final ClassificationWebService service;

    public ClassificationRestController(ClassificationWebService service) {
        this.service = service;
    }

    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(service::findAll);
    }

    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() ->
                service.findById(new ClassificationGetByIdRequest(entityId)));
    }

    @GetMapping(params = "name", produces = JSON)
    public Response findByName(@RequestParam("name") String name) {
        return execute(() ->
                service.findByName(new ClassificationGetByNameRequest(name)));
    }

    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody ClassificationSaveRequest rq) {
        return execute(() -> service.save(rq));
    }

    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody ClassificationUpdateRequest rq) {
        return execute(() -> service.update(rq));
    }

    @DeleteMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() ->
                service.deleteById(new ClassificationDeleteByIdRequest(entityId)));
    }
}

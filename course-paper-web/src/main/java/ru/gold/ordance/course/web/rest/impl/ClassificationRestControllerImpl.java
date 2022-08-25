package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.rest.ClassificationRestController;
import ru.gold.ordance.course.web.service.web.classification.ClassificationWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping("/api/v1/classifications")
public class ClassificationRestControllerImpl implements ClassificationRestController {
    private final ClassificationWebService service;

    public ClassificationRestControllerImpl(ClassificationWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public Response findAll() {
        return execute(service::findAll);
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() ->
                service.findById(new ClassificationGetByIdRequest(entityId)));
    }

    @Override
    @GetMapping(params = "name", produces = JSON)
    public Response findByName(@RequestParam("name") String name) {
        return execute(() ->
                service.findByName(new ClassificationGetByNameRequest(name)));
    }

    @Override
    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody ClassificationSaveRequest rq) {
        return execute(() -> service.save(rq));
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody ClassificationUpdateRequest rq) {
        return execute(() -> service.update(rq));
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() ->
                service.deleteById(new ClassificationDeleteByIdRequest(entityId)));
    }
}

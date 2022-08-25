package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.rest.ClassificationRestController;
import ru.gold.ordance.course.web.service.web.classification.ClassificationWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.utils.RequestUtils.*;

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
        try {
            return service.findAll();
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        try {
            return service.findById(new ClassificationGetByIdRequest(entityId));
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @GetMapping(params = "name", produces = JSON)
    public Response findByName(@RequestParam("name") String name) {
        try {
            return service.findByName(new ClassificationGetByNameRequest(name));
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody ClassificationSaveRequest rq) {
        try {
            return service.save(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody ClassificationUpdateRequest rq) {
        try {
            return service.update(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        try {
            return service.deleteById(new ClassificationDeleteByIdRequest(entityId));
        } catch (Exception e) {
            return createFrom(e);
        }
    }
}

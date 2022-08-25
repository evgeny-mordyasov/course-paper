package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.rest.LanguageRestController;
import ru.gold.ordance.course.web.service.web.language.LanguageWebService;

import static ru.gold.ordance.course.web.api.BaseErrorResponse.createFrom;
import static ru.gold.ordance.course.web.utils.RequestUtils.*;

@RestController
@RequestMapping("/api/v1/languages")
public class LanguageRestControllerImpl implements LanguageRestController {
    private final LanguageWebService service;

    public LanguageRestControllerImpl(LanguageWebService service) {
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
            return service.findById(new LanguageGetByIdRequest(entityId));
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @GetMapping(params = "name", produces = JSON)
    public Response findByName(@RequestParam("name") String name) {
            try {
            return service.findByName(new LanguageGetByNameRequest(name));
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody LanguageSaveRequest rq) {
        try {
            return service.save(rq);
        } catch (Exception e) {
            return createFrom(e);
        }
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody LanguageUpdateRequest rq) {
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
            return service.deleteById(new LanguageDeleteByIdRequest(entityId));
        } catch (Exception e) {
            return createFrom(e);
        }
    }
}

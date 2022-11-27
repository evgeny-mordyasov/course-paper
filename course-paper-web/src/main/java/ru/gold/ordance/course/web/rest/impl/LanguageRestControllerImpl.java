package ru.gold.ordance.course.web.rest.impl;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.request.language.*;
import ru.gold.ordance.course.web.service.web.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.request.Response;
import ru.gold.ordance.course.web.rest.LanguageRestController;
import ru.gold.ordance.course.web.service.web.language.LanguageWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.Language.BASE_URL)
public class LanguageRestControllerImpl implements LanguageRestController {
    private final LanguageWebService service;

    public LanguageRestControllerImpl(LanguageWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    public Response findAll() {
       return execute(service::findAll);
    }

    @Override
    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() ->
                service.findById(new LanguageGetByIdRequest(entityId)));
    }

    @Override
    @GetMapping(params = "name", produces = JSON)
    public Response findByName(@RequestParam("name") String name) {
        return execute(() ->
                service.findByName(new LanguageGetByNameRequest(name)));
    }

    @Override
    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody LanguageSaveRequest rq) {
        return execute(() -> service.save(rq));
    }

    @Override
    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody LanguageUpdateRequest rq) {
        return execute(() -> service.update(rq));
    }

    @Override
    @DeleteMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() ->
                service.deleteById(new LanguageDeleteByIdRequest(entityId)));
    }
}

package ru.gold.ordance.course.web.rest;

import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.course.internal.api.domain.language.request.*;
import ru.gold.ordance.course.web.service.authorization.jwt.rule.Endpoint;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.service.LanguageWebService;

import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.execute;

@RestController
@RequestMapping(Endpoint.Language.BASE_URL)
public class LanguageRestController {
    private final LanguageWebService service;

    public LanguageRestController(LanguageWebService service) {
        this.service = service;
    }

    @GetMapping(produces = JSON)
    public Response findAll() {
       return execute(service::findAll);
    }

    @GetMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response findById(@PathVariable Long entityId) {
        return execute(() ->
                service.findById(new LanguageGetByIdRequest(entityId)));
    }

    @GetMapping(params = "name", produces = JSON)
    public Response findByName(@RequestParam("name") String name) {
        return execute(() ->
                service.findByName(new LanguageGetByNameRequest(name)));
    }

    @PostMapping(consumes = JSON, produces = JSON)
    public Response save(@RequestBody LanguageSaveRequest rq) {
        return execute(() -> service.save(rq));
    }

    @PutMapping(consumes = JSON, produces = JSON)
    public Response update(@RequestBody LanguageUpdateRequest rq) {
        return execute(() -> service.update(rq));
    }

    @DeleteMapping(value = Endpoint.ENTITY_ID_VARIABLE, produces = JSON)
    public Response deleteById(@PathVariable Long entityId) {
        return execute(() ->
                service.deleteById(new LanguageDeleteByIdRequest(entityId)));
    }
}

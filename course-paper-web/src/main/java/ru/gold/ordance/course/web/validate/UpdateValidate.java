package ru.gold.ordance.course.web.validate;

import org.springframework.stereotype.Component;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.utils.StorageHelper;
import ru.gold.ordance.course.web.api.UpdateRequest;
import ru.gold.ordance.course.web.api.classification.ClassificationUpdateRequest;
import ru.gold.ordance.course.web.api.client.ClientUpdateRequest;
import ru.gold.ordance.course.web.api.language.LanguageUpdateRequest;

import static ru.gold.ordance.course.web.validate.ValidateHelper.errorEntityId;
import static ru.gold.ordance.course.web.validate.ValidateHelper.errorString;

@Component
public class UpdateValidate implements RequestValidate<UpdateRequest> {
    private final StorageHelper storage;

    public UpdateValidate(StorageHelper storage) {
        this.storage = storage;
    }

    @Override
    public void validate(UpdateRequest rq) {
        if (rq instanceof ClientUpdateRequest) {
            validateRequest((ClientUpdateRequest) rq);
        } else if (rq instanceof ClassificationUpdateRequest) {
            validateRequest((ClassificationUpdateRequest) rq);
        } else if (rq instanceof LanguageUpdateRequest) {
            validateRequest((LanguageUpdateRequest) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(ClientUpdateRequest rq) {
        errorEntityId(rq.getEntityId());
        errorString(rq.getSurname(), "surname");
        errorString(rq.getName(), "name");
        errorString(rq.getPatronymic(), "patronymic");
        errorString(rq.getPassword(), "password");

        storage.validate(Client.create(rq.getEntityId()));
    }

    private void validateRequest(ClassificationUpdateRequest rq) {
        errorEntityId(rq.getEntityId());
        errorString(rq.getName(), "name");

        storage.validate(Classification.create(rq.getEntityId()));
    }

    private void validateRequest(LanguageUpdateRequest rq) {
        errorEntityId(rq.getEntityId());
        errorString(rq.getName(), "name");

        storage.validate(Language.create(rq.getEntityId()));
    }
}

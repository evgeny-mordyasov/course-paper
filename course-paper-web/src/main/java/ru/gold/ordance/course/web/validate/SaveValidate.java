package ru.gold.ordance.course.web.validate;

import org.springframework.stereotype.Component;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.utils.StorageHelper;
import ru.gold.ordance.course.web.api.Request;
import ru.gold.ordance.course.web.api.SaveRequest;
import ru.gold.ordance.course.web.api.classification.ClassificationSaveRequest;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.language.LanguageSaveRequest;

import java.util.List;

import static ru.gold.ordance.course.web.validate.ValidateHelper.*;

@Component
public class SaveValidate implements RequestValidate<SaveRequest> {
    private final StorageHelper storage;

    public SaveValidate(StorageHelper storage) {
        this.storage = storage;
    }

    @Override
    public void validate(SaveRequest rq) {
        if (rq instanceof ClassificationSaveRequest) {
            validateRequest((ClassificationSaveRequest) rq);
        } else if (rq instanceof LanguageSaveRequest) {
            validateRequest((LanguageSaveRequest) rq);
        } else if (rq instanceof FileSaveRequest) {
            validateRequest((FileSaveRequest) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(ClassificationSaveRequest rq) {
        errorString(rq.getName(), "name");
    }

    private void validateRequest(LanguageSaveRequest rq) {
        errorString(rq.getName(), "name");
    }

    private void validateRequest(FileSaveRequest rq) {
        errorObjectId(rq.getClassificationId(), "classificationId");
        errorObjectId(rq.getLanguageId(), "languageId");
        errorTrue(rq.getFile().isEmpty(), "The file is missing.");
        errorString(rq.getFile().getOriginalFilename(), "fileName");

        storage.validate(
                List.of(Classification.create(rq.getClassificationId()),
                        Language.create(rq.getLanguageId())));
    }
}

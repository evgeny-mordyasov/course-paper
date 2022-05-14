package ru.gold.ordance.course.web.validate;

import ru.gold.ordance.course.web.api.SaveRequest;
import ru.gold.ordance.course.web.api.classification.ClassificationSaveRequest;
import ru.gold.ordance.course.web.api.language.LanguageSaveRequest;

import static ru.gold.ordance.course.web.validate.ValidateHelper.errorString;

public class SaveValidate implements RequestValidate<SaveRequest> {
    @Override
    public void validate(SaveRequest rq) {
        if (rq instanceof ClassificationSaveRequest) {
            validateRequest((ClassificationSaveRequest) rq);
        } else if (rq instanceof LanguageSaveRequest) {
            validateRequest((LanguageSaveRequest) rq);
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
}

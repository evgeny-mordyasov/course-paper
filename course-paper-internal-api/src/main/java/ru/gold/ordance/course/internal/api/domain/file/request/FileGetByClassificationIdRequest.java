package ru.gold.ordance.course.internal.api.domain.file.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorObjectId;

@AllArgsConstructor
@Getter
@ToString
public class FileGetByClassificationIdRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final Long classificationId;

    @Override
    public void validate() {
        errorObjectId(getClassificationId(), "classificationId");
    }
}

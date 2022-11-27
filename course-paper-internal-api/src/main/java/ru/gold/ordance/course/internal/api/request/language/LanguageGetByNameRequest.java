package ru.gold.ordance.course.internal.api.request.language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.request.GetRequest;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@AllArgsConstructor
@Getter
@ToString
public class LanguageGetByNameRequest implements GetRequest {
    private static final long serialVersionUID = 1L;

    private final String name;

    @Override
    public void validate() {
        errorString(getName(), "name");
    }
}

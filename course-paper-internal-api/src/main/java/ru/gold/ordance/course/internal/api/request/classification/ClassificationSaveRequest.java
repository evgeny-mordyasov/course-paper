package ru.gold.ordance.course.internal.api.request.classification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.request.SaveRequest;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ClassificationSaveRequest implements SaveRequest {
    private static final long serialVersionUID = 1L;

    private final String name;

    @Override
    public void validate() {
        errorString(getName(), "name");
    }
}

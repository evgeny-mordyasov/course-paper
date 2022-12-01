package ru.gold.ordance.course.internal.api.domain.file.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class FileDeleteByUrnRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final String urn;

    @Override
    public void validate() {
        errorString(getUrn(), "urn");
    }
}

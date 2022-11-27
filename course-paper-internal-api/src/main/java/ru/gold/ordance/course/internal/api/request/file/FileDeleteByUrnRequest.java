package ru.gold.ordance.course.internal.api.request.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.request.DeleteRequest;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorString;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class FileDeleteByUrnRequest implements DeleteRequest {
    private static final long serialVersionUID = 1L;

    private final String urn;

    @Override
    public void validate() {
        errorString(getUrn(), "urn");
    }
}

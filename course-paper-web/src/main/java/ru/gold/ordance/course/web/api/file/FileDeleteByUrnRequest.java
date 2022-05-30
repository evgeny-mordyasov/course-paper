package ru.gold.ordance.course.web.api.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.course.web.api.DeleteRequest;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.errorString;

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

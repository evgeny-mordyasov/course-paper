package ru.gold.ordance.course.internal.api.domain.history.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.course.internal.api.domain.Request;

import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.errorObjectId;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class HistorySaveRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final Long clientId;
    private final Long documentId;
    private final Long languageId;

    @Override
    public void validate() {
        errorObjectId(getClientId(), "clientId");
        errorObjectId(getDocumentId(), "documentId");
        errorObjectId(getLanguageId(), "languageId");
    }
}

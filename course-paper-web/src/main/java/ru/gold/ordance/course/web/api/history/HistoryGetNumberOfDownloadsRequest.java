package ru.gold.ordance.course.web.api.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.course.web.api.GetRequest;

import static ru.gold.ordance.course.web.utils.ValidatorUtils.errorObjectId;

@AllArgsConstructor
@Getter
@ToString
public class HistoryGetNumberOfDownloadsRequest implements GetRequest {
    private static final long serialVersionUID = 1L;

    private final Long documentId;

    @Override
    public void validate() {
        errorObjectId(getDocumentId(), "documentId");
    }
}

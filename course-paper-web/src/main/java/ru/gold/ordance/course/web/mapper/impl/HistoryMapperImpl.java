package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.internal.api.domain.classification.model.WebClassification;
import ru.gold.ordance.course.internal.api.domain.client.model.WebClient;
import ru.gold.ordance.course.internal.api.domain.file.model.WebDocument;
import ru.gold.ordance.course.internal.api.domain.history.request.HistorySaveRequest;
import ru.gold.ordance.course.internal.api.domain.history.model.WebHistory;
import ru.gold.ordance.course.internal.api.domain.language.model.WebLanguage;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.persistence.entity.impl.History;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.web.mapper.HistoryMapper;

public class HistoryMapperImpl implements HistoryMapper {

    @Override
    public History toHistory(HistorySaveRequest rq) {
        return History.builder()
                .withClient(Client.builder()
                        .withEntityId(rq.getClientId())
                        .build())
                .withDocument(Document.builder()
                        .withEntityId(rq.getDocumentId())
                        .build())
                .withLanguage(Language.builder()
                        .withEntityId(rq.getLanguageId())
                        .build())
                .build();
    }

    @Override
    public WebHistory fromHistory(History history) {
        return WebHistory.builder()
                .withEntityId(history.getEntityId())
                .withClient(WebClient.builder()
                        .entityId(history.getEntityId())
                        .surname(history.getClient().getSurname())
                        .name(history.getClient().getName())
                        .patronymic(history.getClient().getPatronymic())
                        .email(history.getClient().getEmail())
                        .createdDate(history.getClient().getCreatedDate())
                        .lastModifiedDate(history.getClient().getLastModifiedDate())
                        .role(history.getClient().getRole())
                        .isActive(history.getClient().getIsActive())
                        .build())
                .withDocument(WebDocument.builder()
                        .withEntityId(history.getDocument().getEntityId())
                        .withFullName(history.getDocument().getFullName())
                        .withName(history.getDocument().getName())
                        .withExtension(history.getDocument().getExtension())
                        .withClassification(WebClassification.builder()
                                .withEntityId(history.getDocument().getClassification().getEntityId())
                                .withName(history.getDocument().getClassification().getName())
                                .build())
                        .build())
                .withLanguage(WebLanguage.builder()
                        .withEntityId(history.getLanguage().getEntityId())
                        .withName(history.getLanguage().getName())
                        .build())
                .withReadingTime(history.getReadingTime())
                .build();
    }
}

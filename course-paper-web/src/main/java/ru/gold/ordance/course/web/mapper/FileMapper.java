package ru.gold.ordance.course.web.mapper;

import ru.gold.ordance.course.internal.api.dto.File;
import ru.gold.ordance.course.internal.api.domain.classification.model.WebClassification;
import ru.gold.ordance.course.internal.api.domain.file.model.WebDocument;
import ru.gold.ordance.course.internal.api.domain.file.model.WebDocumentLanguage;
import ru.gold.ordance.course.internal.api.domain.file.model.WebFile;
import ru.gold.ordance.course.internal.api.domain.language.model.WebLanguage;
import ru.gold.ordance.course.persistence.entity.impl.Classification;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.persistence.entity.impl.LnkDocumentLanguage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class FileMapper {
    private static final FileMapper INSTANCE = new FileMapper();
    private static final String RESOURCE_URL = "http://localhost:8090/api/v1/files/resource?documentId=%s&languageId=%s";

    private FileMapper() {
    }

    public static FileMapper instance() {
        return INSTANCE;
    }

    public Document toDocument(File stored, Long classificationId) {
        return Document.builder()
                .withClassification(Classification.builder()
                        .withEntityId(classificationId)
                        .build())
                .withFullName(stored.getFullFileName())
                .withName(stored.getFileName())
                .withExtension(stored.getExtension())
                .build();
    }

    public LnkDocumentLanguage toLnk(Document document, Long languageId, String urn) {
        return LnkDocumentLanguage.builder()
                .withDocument(Document.builder()
                        .withEntityId(document.getEntityId())
                        .withClassification(Classification.builder()
                                .withEntityId(document.getClassification().getEntityId())
                                .build())
                        .build())
                .withLanguage(Language.builder()
                        .withEntityId(languageId)
                        .build())
                .withUrn(urn)
                .build();
    }

    public LnkDocumentLanguage toLnk(Long documentId, Long languageId, String urn) {
        return LnkDocumentLanguage.builder()
                .withDocument(Document.builder()
                        .withEntityId(documentId)
                        .withClassification(Classification.builder().build())
                        .build())
                .withLanguage(Language.builder()
                        .withEntityId(languageId)
                        .build())
                .withUrn(urn)
                .build();
    }

    public WebFile toWebFile(Document doc, List<LnkDocumentLanguage> documentLanguages) {
        Classification cl = doc.getClassification();

        return WebFile.builder()
                .withDocument(WebDocument.builder()
                        .withEntityId(doc.getEntityId())
                        .withFullName(doc.getFullName())
                        .withName(doc.getName())
                        .withExtension(doc.getExtension())
                        .withClassification(WebClassification.builder()
                                .withEntityId(cl.getEntityId())
                                .withName(cl.getName())
                                .build())
                        .build())
                .withLanguages(documentLanguages.stream().map(lnk ->
                        WebDocumentLanguage.builder()
                                .withLanguage(WebLanguage.builder()
                                        .withEntityId(lnk.getLanguage().getEntityId())
                                        .withName(lnk.getLanguage().getName())
                                        .build())
                                .withUrn(lnk.getUrn())
                                .withUrl(String.format(RESOURCE_URL, lnk.getDocument().getEntityId(), lnk.getLanguage().getEntityId()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public List<WebFile> toWebFile(Set<Map.Entry<Long, List<LnkDocumentLanguage>>> set) {
        return set.stream()
                .map(e -> toWebFile(e.getValue().get(0).getDocument(), e.getValue()))
                .collect(Collectors.toList());
    }
}

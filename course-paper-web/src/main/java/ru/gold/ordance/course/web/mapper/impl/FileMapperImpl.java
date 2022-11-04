package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.web.api.classification.WebClassification;
import ru.gold.ordance.course.web.api.file.WebDocument;
import ru.gold.ordance.course.web.api.file.WebDocumentLanguage;
import ru.gold.ordance.course.web.api.file.WebFile;
import ru.gold.ordance.course.web.api.language.WebLanguage;
import ru.gold.ordance.course.web.dto.File;
import ru.gold.ordance.course.web.mapper.FileMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.gold.ordance.course.common.utils.FileUtils.getFileExtension;
import static ru.gold.ordance.course.common.utils.FileUtils.getFileName;

public class FileMapperImpl implements FileMapper {
    private static final String RESOURCE_URL = "http://localhost:8090/api/v1/files/resource?documentId=%s&languageId=%s";

    @Override
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

    @Override
    public LnkDocumentLanguage toLnk(Long documentId, Long languageId, String urn) {
        return LnkDocumentLanguage.builder()
                .withDocument(Document.builder()
                        .withEntityId(documentId)
                        .build())
                .withLanguage(Language.builder()
                        .withEntityId(languageId)
                        .build())
                .withUrn(urn)
                .build();
    }

    @Override
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

    @Override
    public List<WebFile> toWebFile(Set<Map.Entry<Long, List<LnkDocumentLanguage>>> set) {
        return set.stream()
                .map(e -> toWebFile(e.getValue().get(0).getDocument(), e.getValue()))
                .collect(Collectors.toList());
    }
}

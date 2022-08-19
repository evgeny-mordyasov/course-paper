package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.WebFile;
import ru.gold.ordance.course.web.mapper.FileMapper;

public class FileMapperImpl implements FileMapper {

    @Override
    public Document toDocument(FileSaveRequest rq) {
        return Document.builder()
                .withName(rq.getFile().getOriginalFilename())
                .withClassification(Classification.builder().withEntityId(rq.getClassificationId()).build())
                .build();
    }

    @Override
    public LnkDocumentLanguage toLnk(Document document, Long languageId, String URN) {
        return LnkDocumentLanguage.builder()
                .withDocument(document)
                .withLanguage(Language.builder().withEntityId(languageId).build())
                .withUrn(URN)
                .build();
    }

    @Override
    public WebFile toWebFile(LnkDocumentLanguage lnk) {
        return WebFile.builder()
                .withDocument(lnk.getDocument())
                .withLanguage(lnk.getLanguage())
                .withUrn(lnk.getUrn())
                .build();
    }
}

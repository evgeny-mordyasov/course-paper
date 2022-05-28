package ru.gold.ordance.course.web.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.WebFile;
import ru.gold.ordance.course.web.service.mapper.FileMapper;

@Component
public class FileMapperImpl implements FileMapper {
    @Override
    public Document toDocument(FileSaveRequest rq) {
        return Document.builder()
                .withName(rq.getFile().getOriginalFilename())
                .withClassification(Classification.builder().withId(rq.getClassificationId()).build())
                .build();
    }

    @Override
    public LnkDocumentLanguage toLnk(Document document, Long languageId, String URN) {
        return LnkDocumentLanguage.builder()
                .withDocument(document)
                .withLanguage(Language.builder().withId(languageId).build())
                .withUrn(URN)
                .build();
    }

    @Override
    public WebFile toWebFile(LnkDocumentLanguage lnk) {
        return WebFile.builder()
                .withDocumentId(lnk.getDocument().getId())
                .withLanguageId(lnk.getLanguage().getId())
                .withClassificationId(lnk.getDocument().getClassification().getId())
                .withUrn(lnk.getUrn())
                .build();
    }
}

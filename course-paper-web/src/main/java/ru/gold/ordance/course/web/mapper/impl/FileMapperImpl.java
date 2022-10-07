package ru.gold.ordance.course.web.mapper.impl;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.web.api.classification.WebClassification;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.WebDocument;
import ru.gold.ordance.course.web.api.file.WebFile;
import ru.gold.ordance.course.web.api.language.WebLanguage;
import ru.gold.ordance.course.web.mapper.FileMapper;

import static ru.gold.ordance.course.common.utils.FileUtils.getFileExtension;
import static ru.gold.ordance.course.common.utils.FileUtils.getFileName;

public class FileMapperImpl implements FileMapper {

    @Override
    public Document toDocument(FileSaveRequest rq) {
        return Document.builder()
                .withClassification(Classification.builder().withEntityId(rq.getClassificationId()).build())
                .withFullName(rq.getFile().getOriginalFilename())
                .withName(getFileName(rq.getFile().getOriginalFilename()))
                .withExtension(getFileExtension(rq.getFile().getOriginalFilename()))
                .build();
    }

    @Override
    public LnkDocumentLanguage toLnk(Long documentId, Long languageId, String URN) {
        return LnkDocumentLanguage.builder()
                .withDocument(Document.builder().withEntityId(documentId).build())
                .withLanguage(Language.builder().withEntityId(languageId).build())
                .withUrn(URN)
                .build();
    }

    @Override
    public WebFile toWebFile(LnkDocumentLanguage lnk) {
        Document doc = lnk.getDocument();
        Classification cl = lnk.getDocument().getClassification();
        Language lang = lnk.getLanguage();

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
                .withLanguage(WebLanguage.builder()
                        .withEntityId(lang.getEntityId())
                        .withName(lang.getName())
                        .build())
                .withUrn(lnk.getUrn())
                .build();
    }

    @Override
    public WebFile toWebFileResource(LnkDocumentLanguage lnk) {
       return toWebFile(lnk)
               .toBuilder()
               .withUrn("http://localhost:8090/api/v1/files/resource/" + lnk.getEntityId())
               .build();
    }
}

package ru.gold.ordance.course.web.service.web.file.helper;

import one.util.streamex.StreamEx;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.core.sub.DocumentService;
import ru.gold.ordance.course.base.service.core.sub.LnkDocumentLanguageService;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.mapper.FileMapper;

import java.util.Collections;
import java.util.List;

public class FileDatabaseHelper {
    private final DocumentService documentService;
    private final LnkDocumentLanguageService lnkService;
    private final FileMapper mapper;

    public FileDatabaseHelper(DocumentService documentService,
                              LnkDocumentLanguageService lnkService,
                              FileMapper mapper) {
        this.documentService = documentService;
        this.lnkService = lnkService;
        this.mapper = mapper;
    }

    public FileGetListResponse findAll() {
        List<LnkDocumentLanguage> allFiles = lnkService.findAll();
        var documentAndLanguages = StreamEx.of(allFiles)
                .groupingBy(f -> f.getDocument().getEntityId())
                .entrySet();

        return FileGetListResponse.success(mapper.toWebFile(documentAndLanguages));
    }

    public FileGetEntityResponse findById(FileGetByIdRequest rq) {
        List<LnkDocumentLanguage> documentLanguages = lnkService.findByDocumentId(rq.getEntityId());
        Document document = documentService.findByEntityId(rq.getEntityId());

        return FileGetEntityResponse.success(mapper.toWebFile(document, documentLanguages));
    }

    public String getUrn(Long documentId, Long languageId) {
        LnkDocumentLanguage lnk = lnkService.findByDocumentIdAndLanguageId(documentId, languageId);

        return lnk.getUrn();
    }

    public FileSaveResponse save(FileSaveRequest rq, String urn) {
        Document document = documentService.save(mapper.toDocument(rq));
        LnkDocumentLanguage lnk = lnkService.save(
                mapper.toLnk(document.getEntityId(), rq.getLanguageId(), urn));

        return FileSaveResponse.success(mapper.toWebFile(document, Collections.singletonList(lnk)));
    }

    public FileSaveResponse patch(FilePatchRequest rq, String urn) {
        Document document = documentService.findByEntityId(rq.getDocumentId());
        lnkService.save(mapper.toLnk(rq.getDocumentId(), rq.getLanguageId(), urn));

        List<LnkDocumentLanguage> lnk = lnkService.findByDocumentId(document.getEntityId());

        return FileSaveResponse.success(mapper.toWebFile(document, lnk));
    }

    public void deleteByUrn(FileDeleteByUrnRequest rq) {
        LnkDocumentLanguage foundLnk = lnkService.findByUrn(rq.getUrn());

        deleteRecordInDatabase(foundLnk);
    }

    private void deleteRecordInDatabase(LnkDocumentLanguage lnk) {
        if (isOneRecordByDocumentId(lnk.getDocument().getEntityId())) {
            documentService.deleteByEntityId(lnk.getDocument().getEntityId());
        } else {
            lnkService.deleteByUrn(lnk.getUrn());
        }
    }

    private boolean isOneRecordByDocumentId(Long documentId) {
        Long quantityByDocumentId = lnkService.getQuantityByDocumentId(documentId);
        return quantityByDocumentId == 1;
    }
}

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
        Document document = documentLanguages.get(0).getDocument();

        return FileGetEntityResponse.success(mapper.toWebFile(document, documentLanguages));
    }

    public FileGetEntityResponse findByDocumentIdAndLanguageId(Long documentId, Long languageId) {
        LnkDocumentLanguage lnk = lnkService.findByDocumentIdAndLanguageId(documentId, languageId);
        Document document = lnk.getDocument();

        return FileGetEntityResponse.success(mapper.toWebFile(document, Collections.singletonList(lnk)));
    }

    public FileSaveResponse save(FileSaveRequest rq) {
        Document document = documentService.save(mapper.toDocument(rq));
        LnkDocumentLanguage lnk = lnkService.save(
                mapper.toLnk(document.getEntityId(), rq.getLanguageId(), rq.getUrn()));

        return FileSaveResponse.success(mapper.toWebFile(document, Collections.singletonList(lnk)));
    }

    public FileSaveResponse patch(FilePatchRequest rq) {
        Document document = documentService.findByEntityId(rq.getDocumentId());
        LnkDocumentLanguage lnk = lnkService.save(
                mapper.toLnk(rq.getDocumentId(), rq.getLanguageId(), rq.getUrn()));

        return FileSaveResponse.success(mapper.toWebFile(document, Collections.singletonList(lnk)));
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

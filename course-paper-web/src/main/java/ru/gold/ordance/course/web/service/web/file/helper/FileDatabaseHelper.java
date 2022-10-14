package ru.gold.ordance.course.web.service.web.file.helper;

import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.DocumentService;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.mapper.FileMapper;

import java.util.List;
import java.util.stream.Collectors;

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

        return FileGetListResponse.success(
                allFiles.stream()
                        .map(mapper::toWebFile)
                        .collect(Collectors.toList()));
    }

    public FileGetEntityResponse findById(FileGetByIdRequest rq) {
        LnkDocumentLanguage foundLnk = lnkService.findByEntityId(rq.getEntityId());

        return FileGetEntityResponse.success(mapper.toWebFile(foundLnk));
    }

    public FileSaveResponse save(FileSaveRequest rq) {
        Long documentId = documentService.save(mapper.toDocument(rq)).getEntityId();
        LnkDocumentLanguage lnk = lnkService.save(
                mapper.toLnk(documentId, rq.getLanguageId(), rq.getUrn()));

        return FileSaveResponse.success(mapper.toWebFile(lnk));
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
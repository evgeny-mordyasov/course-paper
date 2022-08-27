package ru.gold.ordance.course.web.service.web.file.helper;

import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.DocumentService;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;
import ru.gold.ordance.course.web.api.file.FileGetResponse;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.FileSaveResponse;
import ru.gold.ordance.course.web.exception.FileNotFoundException;
import ru.gold.ordance.course.web.mapper.FileMapper;

import java.util.List;
import java.util.Optional;
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

    public FileGetResponse findAll() {
        List<LnkDocumentLanguage> allFiles = lnkService.findAll();

        return FileGetResponse.success(
                allFiles.stream()
                        .map(mapper::toWebFile)
                        .collect(Collectors.toList()));
    }

    public FileSaveResponse save(FileSaveRequest rq) {
        Long documentId = documentService.save(mapper.toDocument(rq)).getEntityId();
        LnkDocumentLanguage lnk = lnkService.save(
                mapper.toLnk(documentId, rq.getLanguageId(), rq.getUrn()));

        return FileSaveResponse.success(mapper.toWebFile(lnk));
    }

    public void deleteByUrn(FileDeleteByUrnRequest rq) {
        Optional<LnkDocumentLanguage> foundLnk = lnkService.findByUrn(rq.getUrn());

        if (foundLnk.isEmpty()) {
            throw new FileNotFoundException("The file '" + rq.getUrn() + "' not found.");
        }

        deleteRecordInDatabase(foundLnk.get());
    }

    private void deleteRecordInDatabase(LnkDocumentLanguage lnk) {
        if (isOneRecordByDocumentId(lnk.getDocument().getEntityId())) {
            documentService.deleteByEntityId(lnk.getDocument().getEntityId());
        } else {
            lnkService.deleteByUrn(lnk.getUrn());
        }
    }

    private boolean isOneRecordByDocumentId(Long documentId) {
        Long quantityByDocumentId = lnkService.findQuantityByDocumentId(documentId);
        return quantityByDocumentId == 1;
    }
}

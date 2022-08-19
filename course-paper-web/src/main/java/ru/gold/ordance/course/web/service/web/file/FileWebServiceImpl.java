package ru.gold.ordance.course.web.service.web.file;

import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.DocumentService;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.mapper.FileMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileWebServiceImpl implements FileWebService {
    private final DocumentService documentService;
    private final LnkDocumentLanguageService lnkService;
    private final FileMapper mapper;
    private final FileStorage fileStorage;

    public FileWebServiceImpl(DocumentService documentService,
                              LnkDocumentLanguageService lnkService,
                              FileMapper mapper,
                              FileStorage fileStorage) {
        this.documentService = documentService;
        this.lnkService = lnkService;
        this.mapper = mapper;
        this.fileStorage = fileStorage;
    }

    @Override
    public FileGetResponse findAll() {
        List<LnkDocumentLanguage> allFiles = lnkService.findAll();

        return FileGetResponse.success(
                allFiles.stream()
                        .map(mapper::toWebFile)
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public FileSaveResponse save(FileSaveRequest rq) throws IOException {
        String URN = fileStorage.getURN(rq);

        LnkDocumentLanguage savedDocument = saveDocument(rq, URN);
        fileStorage.moveFileTo(URN, rq.getFile());

        return FileSaveResponse.success(mapper.toWebFile(savedDocument));
    }

    private LnkDocumentLanguage saveDocument(FileSaveRequest rq, String URN) {
        Document savedDocument = documentService.save(mapper.toDocument(rq));
        return lnkService.save(mapper.toLnk(savedDocument, rq.getLanguageId(), URN));
    }

    @Override
    @Transactional
    public FileDeleteResponse deleteByUrn(FileDeleteByUrnRequest rq) throws IOException {
        Optional<LnkDocumentLanguage> foundLnk = lnkService.findByUrn(rq.getUrn());

        if (foundLnk.isPresent()) {
            deleteRecordInDatabase(foundLnk.get());
            fileStorage.deleteFileByUrn(foundLnk.get().getUrn());
        }

        return FileDeleteResponse.success();
    }

    private void deleteRecordInDatabase(LnkDocumentLanguage lnk) {
        if (isOneRecordByDocumentId(lnk.getDocument().getId())) {
            documentService.deleteById(lnk.getDocument().getId());
        } else {
            lnkService.deleteByUrn(lnk.getUrn());
        }
    }

    private boolean isOneRecordByDocumentId(Long documentId) {
        Long quantityByDocumentId = lnkService.findQuantityByDocumentId(documentId);
        return quantityByDocumentId == 1;
    }
}

package ru.gold.ordance.course.web.service.file;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.DocumentService;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;
import ru.gold.ordance.course.web.api.file.FileGetResponse;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.FileSaveResponse;
import ru.gold.ordance.course.web.service.mapper.FileMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
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

        saveDocument(rq, URN);
        fileStorage.moveFileTo(URN, rq.getFile());

        return FileSaveResponse.success();
    }

    private void saveDocument(FileSaveRequest rq, String URN) {
        Document savedDocument = documentService.save(mapper.toDocument(rq));
        lnkService.save(mapper.toLnk(savedDocument, rq.getLanguageId(), URN));
    }
}

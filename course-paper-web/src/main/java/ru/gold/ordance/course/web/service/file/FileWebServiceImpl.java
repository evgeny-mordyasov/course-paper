package ru.gold.ordance.course.web.service.file;

import org.springframework.stereotype.Service;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.DocumentService;
import ru.gold.ordance.course.base.service.LnkDocumentLanguageService;
import ru.gold.ordance.course.web.api.file.FileGetResponse;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.api.file.FileSaveResponse;
import ru.gold.ordance.course.web.api.file.WebFile;
import ru.gold.ordance.course.web.service.mapper.FileMapper;

import java.io.IOException;
import java.util.Collections;
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
        List<LnkDocumentLanguage> list = lnkService.findAll();

        if (list.isEmpty()) {
            return FileGetResponse.success(Collections.emptyList());
        } else {
            List<WebFile> webFiles = list.stream()
                    .map(mapper::toWebFile)
                    .collect(Collectors.toList());

            return FileGetResponse.success(webFiles);
        }
    }

    @Override
    @SuppressWarnings("all")
    public FileSaveResponse save(FileSaveRequest rq) throws IOException {
        String urn = fileStorage.getUrn(rq);
        fileStorage.moveFileTo(rq.getFile(), urn);

        Document savedDocument = documentService.save(mapper.toDocument(rq));

        lnkService.save(LnkDocumentLanguage.builder()
                .withDocument(savedDocument)
                .withLanguage(Language.builder()
                        .withId(rq.getLanguageId())
                        .build())
                .withUrn(urn)
                .build());

        return FileSaveResponse.success();
    }
}

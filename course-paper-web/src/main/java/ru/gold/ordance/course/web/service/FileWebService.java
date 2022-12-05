package ru.gold.ordance.course.web.service;

import one.util.streamex.StreamEx;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.service.core.DocumentService;
import ru.gold.ordance.course.base.service.core.LanguageService;
import ru.gold.ordance.course.base.service.core.LnkDocumentLanguageService;
import ru.gold.ordance.course.common.exception.EntityNotFoundException;
import ru.gold.ordance.course.common.exception.FileAlreadyExistsException;
import ru.gold.ordance.course.internal.api.domain.EmptySuccessfulResponse;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.internal.api.domain.file.model.WebFile;
import ru.gold.ordance.course.internal.api.domain.file.request.*;
import ru.gold.ordance.course.internal.api.domain.file.response.*;
import ru.gold.ordance.course.persistence.entity.impl.Document;
import ru.gold.ordance.course.persistence.entity.impl.Language;
import ru.gold.ordance.course.persistence.entity.impl.LnkDocumentLanguage;
import ru.gold.ordance.course.web.dto.FileResource;
import ru.gold.ordance.course.web.mapper.FileMapper;
import ru.gold.ordance.course.web.mapper.LanguageMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.gold.ordance.course.persistence.repository.main.EntityRepository.getEntity;

public class FileWebService implements WebService {
    private final DocumentService documentService;
    private final LnkDocumentLanguageService lnkService;
    private final LanguageService languageService;
    private final FileMapper fileMapper;
    private final LanguageMapper languageMapper;

    public FileWebService(DocumentService documentService, LnkDocumentLanguageService lnkService, LanguageService languageService) {
        this.documentService = documentService;
        this.lnkService = lnkService;
        this.languageService = languageService;
        this.fileMapper = FileMapper.instance();
        this.languageMapper = LanguageMapper.instance();
    }

    public FileGetListResponse findAll() {
        List<LnkDocumentLanguage> allFiles = lnkService.findAll();

        return FileGetListResponse.success(getFiles(allFiles));
    }

    private List<WebFile> getFiles(List<LnkDocumentLanguage> files) {
        var documentAndLanguages = StreamEx.of(files)
                .groupingBy(LnkDocumentLanguage::getDocument)
                .entrySet();

        return fileMapper.toWebFile(documentAndLanguages);
    }

    public Response findById(FileGetByIdRequest rq) {
        List<LnkDocumentLanguage> documentLanguages = lnkService.findByDocumentId(rq.getEntityId());

        if (documentLanguages.isEmpty()) {
            return new EmptySuccessfulResponse();
        }

        return FileGetEntityResponse.success(fileMapper.toWebFile(documentLanguages.get(0).getDocument(), documentLanguages));
    }

    public FileGetListResponse getFilesByClassificationId(FileGetByClassificationIdRequest rq) {
        List<LnkDocumentLanguage> list = lnkService.findByClassificationId(rq.getClassificationId());

        return FileGetListResponse.success(getFiles(list));
    }

    public FileGetFreeLanguagesByIdResponse getFreeLanguages(FileGetFreeLanguagesByIdRequest rq) {
        List<Language> languagesDocument =
                lnkService.findByDocumentId(rq.getEntityId())
                        .stream()
                        .map(LnkDocumentLanguage::getLanguage)
                        .collect(Collectors.toList());

        if (languagesDocument.isEmpty()) {
            throw new EntityNotFoundException();
        }

        List<Language> allLanguages = languageService.findAll();
        allLanguages.removeAll(languagesDocument);

        return FileGetFreeLanguagesByIdResponse.success(
                allLanguages.stream()
                        .map(languageMapper::fromLanguage)
                        .collect(Collectors.toList()));
    }

    @Transactional
    public FileSaveResponse save(FileSaveRequest rq) throws IOException {
        Document document = documentService.save(fileMapper.toDocument(rq.getFile(), rq.getClassificationId()));
        LnkDocumentLanguage lnk = lnkService.save(
                fileMapper.toLnk(document.getEntityId(), rq.getLanguageId(), rq.getFile().getInputStream().readAllBytes()));

        return FileSaveResponse.success(fileMapper.toWebFile(document, Collections.singletonList(lnk)));
    }

    @Transactional
    public FileSaveResponse patch(FilePatchRequest rq) throws IOException {
        if (isExistsLanguageForDocument(rq.getLanguageId(), rq.getDocumentId())) {
            throw new FileAlreadyExistsException("The language of the file already exists.");
        }

        Document document = getEntity(documentService.findByEntityId(rq.getDocumentId()));
        lnkService.save(fileMapper.toLnk(rq.getDocumentId(), rq.getLanguageId(), rq.getFile().getInputStream().readAllBytes()));

        List<LnkDocumentLanguage> lnk = lnkService.findByDocumentId(document.getEntityId());

        return FileSaveResponse.success(fileMapper.toWebFile(document, lnk));
    }

    private boolean isExistsLanguageForDocument(Long languageId, Long documentId) {
        Optional<LnkDocumentLanguage> lnk = lnkService.findByDocumentIdAndLanguageId(documentId, languageId);
        return lnk.isPresent();
    }

    @Transactional
    public FileResource load(FileGetByIdAndLanguageIdRequest rq) {
        LnkDocumentLanguage lnk = getEntity(lnkService.findByDocumentIdAndLanguageId(rq.getDocumentId(), rq.getLanguageId()));

        return FileResource.builder()
                .withData(new ByteArrayResource(lnk.getData()))
                .withFullFileName(lnk.getDocument().getFullName())
                .build();
    }

    public FileDeleteResponse deleteById(FileDeleteByIdRequest rq) {
        documentService.deleteByEntityId(rq.getEntityId());

        return FileDeleteResponse.success();
    }

    public FileDeleteResponse deleteByDocumentIdAndLanguageId(FileDeleteByIdAndLanguageIdRequest rq) {
        LnkDocumentLanguage lnk = getEntity(lnkService.findByDocumentIdAndLanguageId(rq.getDocumentId(), rq.getLanguageId()));
        deleteRecordInDatabase(lnk);

        return FileDeleteResponse.success();
    }

    private void deleteRecordInDatabase(LnkDocumentLanguage lnk) {
        if (isOneRecordByDocumentId(lnk.getDocument().getEntityId())) {
            documentService.deleteByEntityId(lnk.getDocument().getEntityId());
        } else {
            lnkService.deleteByEntityId(lnk.getEntityId());
        }
    }

    private boolean isOneRecordByDocumentId(Long documentId) {
        Long quantityByDocumentId = lnkService.getQuantityByDocumentId(documentId);
        return quantityByDocumentId == 1;
    }

}

package ru.gold.ordance.course.web.service.web.file.helper;

import one.util.streamex.StreamEx;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.service.core.sub.DocumentService;
import ru.gold.ordance.course.base.service.core.sub.LanguageService;
import ru.gold.ordance.course.base.service.core.sub.LnkDocumentLanguageService;
import ru.gold.ordance.course.web.api.file.*;
import ru.gold.ordance.course.web.api.language.WebLanguage;
import ru.gold.ordance.course.web.dto.File;
import ru.gold.ordance.course.web.mapper.FileMapper;
import ru.gold.ordance.course.web.mapper.LanguageMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileDatabaseHelper {
    private final DocumentService documentService;
    private final LnkDocumentLanguageService lnkService;
    private final LanguageService languageService;
    private final FileMapper fileMapper;
    private final LanguageMapper languageMapper;

    public FileDatabaseHelper(DocumentService documentService,
                              LnkDocumentLanguageService lnkService,
                              LanguageService languageService,
                              FileMapper fileMapper,
                              LanguageMapper languageMapper) {
        this.documentService = documentService;
        this.lnkService = lnkService;
        this.languageService = languageService;
        this.fileMapper = fileMapper;
        this.languageMapper = languageMapper;
    }

    public List<WebFile> findAll() {
        List<LnkDocumentLanguage> allFiles = lnkService.findAll();
        var documentAndLanguages = StreamEx.of(allFiles)
                .groupingBy(f -> f.getDocument().getEntityId())
                .entrySet();

        return fileMapper.toWebFile(documentAndLanguages);
    }

    public WebFile findById(FileGetByIdRequest rq) {
        List<LnkDocumentLanguage> documentLanguages = lnkService.getByDocumentId(rq.getEntityId());
        Document document = documentService.getByEntityId(rq.getEntityId());

        return fileMapper.toWebFile(document, documentLanguages);
    }

    public String getUrn(Long documentId, Long languageId) {
        LnkDocumentLanguage lnk = lnkService.getByDocumentIdAndLanguageId(documentId, languageId);

        return lnk.getUrn();
    }

    public List<WebLanguage> getFreeLanguages(FileGetFreeLanguagesByIdRequest rq) {
        List<Language> languagesDocument =
                lnkService.getByDocumentId(rq.getEntityId()).stream()
                .map(LnkDocumentLanguage::getLanguage)
                .collect(Collectors.toList());

        List<Language> allLanguages = languageService.findAll();

        allLanguages.removeAll(languagesDocument);

        return allLanguages.stream()
                .map(languageMapper::fromLanguage)
                .collect(Collectors.toList());
    }

    public WebFile save(File stored, Long classificationId, Long languageId) {
        Document document = documentService.save(fileMapper.toDocument(stored, classificationId));
        LnkDocumentLanguage lnk = lnkService.save(
                fileMapper.toLnk(document.getEntityId(), languageId, stored.getUrn()));

        return fileMapper.toWebFile(document, Collections.singletonList(lnk));
    }

    public WebFile patch(FilePatchRequest rq, String urn) {
        Document document = documentService.getByEntityId(rq.getDocumentId());
        lnkService.save(fileMapper.toLnk(rq.getDocumentId(), rq.getLanguageId(), urn));

        List<LnkDocumentLanguage> lnk = lnkService.getByDocumentId(document.getEntityId());

        return fileMapper.toWebFile(document, lnk);
    }

    public boolean isExistsLanguageForDocument(Long languageId, Long documentId) {
        Optional<LnkDocumentLanguage> lnk = lnkService.findByDocumentIdAndLanguageId(documentId, languageId);

        return lnk.isPresent();
    }

    public void deleteByUrn(FileDeleteByUrnRequest rq) {
        LnkDocumentLanguage foundLnk = lnkService.getByUrn(rq.getUrn());

        deleteRecordInDatabase(foundLnk);
    }

    public List<String> deleteById(FileDeleteByIdRequest rq) {
        List<LnkDocumentLanguage> foundLnk = lnkService.getByDocumentId(rq.getEntityId());
        foundLnk.forEach(this::deleteRecordInDatabase);

        return foundLnk.stream()
                .map(LnkDocumentLanguage::getUrn)
                .collect(Collectors.toList());
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

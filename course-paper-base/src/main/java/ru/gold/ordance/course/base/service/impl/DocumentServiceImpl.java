package ru.gold.ordance.course.base.service.impl;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.persistence.DocumentRepository;
import ru.gold.ordance.course.base.service.DocumentService;
import ru.gold.ordance.course.base.utils.StorageHelper;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class DocumentServiceImpl implements DocumentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private final DocumentRepository repository;

    public DocumentServiceImpl(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Document> findAll() {
        LOGGER.info("The search for all document has started.");

        List<Document> documents = repository.findAll();

        LOGGER.info("Size of list: {}", documents.size());

        return documents;
    }

    @Override
    public Optional<Document> findById(@NotNull Long id) {
        LOGGER.info("The search by id document has started.");

        Optional<Document> document = repository.findById(id);

        if (document.isEmpty()) {
            LOGGER.info("The document not found. entityId = {}", id);
        } else {
            LOGGER.info("The document was found. document = {}", document.get());
        }

        return document;
    }

    @Override
    public List<Document> findByName(@NotNull String name) {
        LOGGER.info("The search for documents by name has started.");

        List<Document> documents = repository.findAllByName(name);

        LOGGER.info("Size of list: {}", documents.size());

        return documents;
    }

    @Override
    public Document save(@NotNull Document document) {
        LOGGER.info("The save document has started.");

        Document saved = repository.saveAndFlush(document);

        LOGGER.info("The save document has finished.");

        return saved;
    }

    @Override
    public void update(@NotNull Document document) {
        LOGGER.info("The update document has started.");

        repository.saveAndFlush(document);

        LOGGER.info("The update document has finished.");
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("The delete document has started.");

        Optional<Document> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The document was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The document by id does not exist. entityId = {}", id);
        }
    }
}

package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.History;
import ru.gold.ordance.course.base.entity.Language;

import java.util.List;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.*;

@Repository
public interface HistoryRepository extends EntityRepository<History> {
    List<History> findByDocument_EntityId(Long documentId);

    @Override
    default History preserve(History entity) {
        return EntityRepository.super.preserve(fillEntity(entity));
    }

    private History fillEntity(History entity) {
        Client client = getClientById(entity.getClient().getEntityId());
        Document document = getDocumentById(entity.getDocument().getEntityId());
        Language language = getLanguageById(entity.getLanguage().getEntityId());

        return entity.toBuilder()
                .withClient(client)
                .withDocument(document)
                .withLanguage(language)
                .build();
    }
}

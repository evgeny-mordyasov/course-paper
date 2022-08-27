package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.Document;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.*;

@Repository
public interface LnkDocumentLanguageRepository extends EntityRepository<LnkDocumentLanguage> {
    Optional<LnkDocumentLanguage> findLnkDocumentLanguageByUrn(String URN);
    Optional<LnkDocumentLanguage> findLnkDocumentLanguageByDocument_EntityIdAndLanguage_EntityIdAndUrn(Long docId, Long langId, String urn);
    Long countLnkDocumentLanguagesByDocument_EntityId(Long documentId);

    @Override
    default LnkDocumentLanguage preserve(LnkDocumentLanguage entity) {
        return EntityRepository.super.preserve(fillEntity(entity));
    }

    @Override
    default LnkDocumentLanguage update(LnkDocumentLanguage entity) {
        throw new UnsupportedOperationException("The update method not supported.");
    }

    default void deleteByUrn(String urn) {
        Optional<LnkDocumentLanguage> lnk = findLnkDocumentLanguageByUrn(urn);

        if (lnk.isEmpty()) {
            throw new EntityNotFoundException();
        }

        deleteById(lnk.get().getEntityId());
    }

    private LnkDocumentLanguage fillEntity(LnkDocumentLanguage entity) {
        validate(entity);

        Language language = getLanguageById(entity.getLanguage().getEntityId());
        Document document = getDocumentById(entity.getDocument().getEntityId());

        return entity.toBuilder()
                .withLanguage(language)
                .withDocument(document)
                .build();
    }

    private void validate(LnkDocumentLanguage entity) {
        Optional<LnkDocumentLanguage> fromStorage =
                findLnkDocumentLanguageByDocument_EntityIdAndLanguage_EntityIdAndUrn(
                        entity.getDocument().getEntityId(),
                        entity.getLanguage().getEntityId(),
                        entity.getUrn());


        if (fromStorage.isPresent()) {
            throw new ViolatesConstraintException();
        }
    }
}

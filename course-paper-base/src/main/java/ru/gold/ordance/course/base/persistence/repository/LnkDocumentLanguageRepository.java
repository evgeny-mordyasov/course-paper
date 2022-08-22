package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.LnkDocumentLanguage;
import ru.gold.ordance.course.base.exception.EntityNotFoundException;
import ru.gold.ordance.course.base.exception.InternalEntityNotFoundException;
import ru.gold.ordance.course.base.exception.ViolatesConstraintException;

import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.noExistsDocument;
import static ru.gold.ordance.course.base.persistence.PersistenceHelper.noExistsLanguage;

@Repository
public interface LnkDocumentLanguageRepository extends EntityRepository<LnkDocumentLanguage> {
    Optional<LnkDocumentLanguage> findLnkDocumentLanguageByUrn(String URN);
    Optional<LnkDocumentLanguage> findLnkDocumentLanguageByDocument_EntityIdAndLanguage_EntityIdAndUrn(Long docId, Long langId, String urn);
    Long countLnkDocumentLanguagesByDocument_EntityId(Long documentId);

    @Override
    default LnkDocumentLanguage preserve(LnkDocumentLanguage entity) {
        validate(entity);
        return EntityRepository.super.preserve(entity);
    }

    @Override
    default LnkDocumentLanguage update(LnkDocumentLanguage entity) {
        validate(entity);
        return EntityRepository.super.update(entity);
    }

    default void deleteByUrn(String urn) {
        Optional<LnkDocumentLanguage> lnk = findLnkDocumentLanguageByUrn(urn);

        if (lnk.isEmpty()) {
            throw new EntityNotFoundException();
        }

        deleteById(lnk.get().getEntityId());
    }

    private void validate(LnkDocumentLanguage entity) {
        long languageId = entity.getLanguage().getEntityId();
        long documentId = entity.getDocument().getEntityId();

        if (noExistsLanguage(languageId) || noExistsDocument(documentId)) {
            throw new InternalEntityNotFoundException();
        }

        Optional<LnkDocumentLanguage> fromStorage =
                findLnkDocumentLanguageByDocument_EntityIdAndLanguage_EntityIdAndUrn(documentId, languageId, entity.getUrn());

        fromStorage.ifPresent(lnkFromStorage -> {
            if (!lnkFromStorage.getEntityId().equals(entity.getEntityId())) {
                throw new ViolatesConstraintException();
            }
        });
    }
}

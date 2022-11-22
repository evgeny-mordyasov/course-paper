package ru.gold.ordance.course.base.persistence.repository;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.base.entity.ConfirmationToken;

import java.util.Optional;

import static ru.gold.ordance.course.base.persistence.PersistenceHelper.getEntity;

@Repository
public interface ConfirmationTokenRepository extends EntityRepository<ConfirmationToken> {
    Optional<ConfirmationToken> findByToken(String token);

    default ConfirmationToken getByToken(String token) {
        return getEntity(findByToken(token));
    }
}

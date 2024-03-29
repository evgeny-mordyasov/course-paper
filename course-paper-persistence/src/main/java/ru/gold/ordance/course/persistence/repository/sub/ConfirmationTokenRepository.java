package ru.gold.ordance.course.persistence.repository.sub;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;
import ru.gold.ordance.course.persistence.repository.main.EntityRepository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends EntityRepository<ConfirmationToken> {
    Optional<ConfirmationToken> findByClient_EntityIdAndAndToken(Long clientId, String token);

    default ConfirmationToken preserve(ConfirmationToken confirmationToken) {
        return defaultPreserve(confirmationToken);
    }
}

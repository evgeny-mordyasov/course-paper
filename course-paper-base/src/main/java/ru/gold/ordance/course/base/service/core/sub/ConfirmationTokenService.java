package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.service.core.NotStandardEntityService;
import ru.gold.ordance.course.persistence.entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService extends NotStandardEntityService {
    ConfirmationToken save(ConfirmationToken entity);
    Optional<ConfirmationToken> findByToken(String token);
}

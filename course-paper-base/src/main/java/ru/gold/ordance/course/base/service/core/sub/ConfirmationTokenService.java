package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.base.entity.ConfirmationToken;
import ru.gold.ordance.course.base.service.core.NotStandardEntityService;
import ru.gold.ordance.course.base.service.core.NotUpdatableEntityService;

public interface ConfirmationTokenService extends NotStandardEntityService {
    ConfirmationToken save(ConfirmationToken entity);
    ConfirmationToken getByToken(String token);
}

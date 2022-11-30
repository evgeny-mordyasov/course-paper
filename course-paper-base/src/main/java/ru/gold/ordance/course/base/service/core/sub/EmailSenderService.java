package ru.gold.ordance.course.base.service.core.sub;

import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;

public interface EmailSenderService {
    void send(ConfirmationToken token);
}

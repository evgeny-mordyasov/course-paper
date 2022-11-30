package ru.gold.ordance.course.web.service.web.authorization;

import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;

public interface EmailSenderWebService {
    void send(Client client);
    ConfirmationToken getByToken(String token);
}

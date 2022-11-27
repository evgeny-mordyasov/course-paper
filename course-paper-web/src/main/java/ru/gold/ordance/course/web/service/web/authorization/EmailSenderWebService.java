package ru.gold.ordance.course.web.service.web.authorization;

import ru.gold.ordance.course.persistence.entity.Client;
import ru.gold.ordance.course.persistence.entity.ConfirmationToken;

public interface EmailSenderWebService {
    void send(Client client);
    ConfirmationToken getByToken(String token);
}

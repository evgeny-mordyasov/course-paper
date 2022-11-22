package ru.gold.ordance.course.web.service.web.authorization;

import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.ConfirmationToken;

public interface EmailSenderWebService {
    void send(Client client);
    ConfirmationToken getByToken(String token);
}

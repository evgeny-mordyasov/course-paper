package ru.gold.ordance.course.web.service.web.authorization.impl;

import org.springframework.scheduling.annotation.Async;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.entity.ConfirmationToken;
import ru.gold.ordance.course.base.service.core.sub.ConfirmationTokenService;
import ru.gold.ordance.course.base.service.core.sub.EmailSenderService;
import ru.gold.ordance.course.web.service.web.authorization.EmailSenderWebService;

public class EmailSenderWebServiceImpl implements EmailSenderWebService {
    private final EmailSenderService service;
    private final ConfirmationTokenService confirmationTokenService;

    public EmailSenderWebServiceImpl(EmailSenderService service, ConfirmationTokenService confirmationTokenService) {
        this.service = service;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public void send(Client client) {
        ConfirmationToken token = createToken(client);
        new Thread(() -> service.send(token)).start();
    }

    private ConfirmationToken createToken(Client client) {
        ConfirmationToken token = ConfirmationToken.builder()
                .withClient(client)
                .build();

        confirmationTokenService.save(token);

        return token;
    }

    @Override
    public ConfirmationToken getByToken(String token) {
        return confirmationTokenService.getByToken(token);
    }
}

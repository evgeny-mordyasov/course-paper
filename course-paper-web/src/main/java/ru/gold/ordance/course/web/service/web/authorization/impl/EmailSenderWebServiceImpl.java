package ru.gold.ordance.course.web.service.web.authorization.impl;

import ru.gold.ordance.course.base.service.core.ConfirmationTokenService;
import ru.gold.ordance.course.base.service.core.MailSenderService;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;
import ru.gold.ordance.course.web.service.web.authorization.EmailSenderWebService;

import static ru.gold.ordance.course.persistence.repository.main.EntityRepository.getEntity;

public class EmailSenderWebServiceImpl implements EmailSenderWebService {
    private final MailSenderService service;
    private final ConfirmationTokenService confirmationTokenService;

    public EmailSenderWebServiceImpl(MailSenderService service, ConfirmationTokenService confirmationTokenService) {
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
        return getEntity(confirmationTokenService.findByToken(token));
    }
}

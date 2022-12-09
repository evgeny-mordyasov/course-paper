package ru.gold.ordance.course.web.service;

import ru.gold.ordance.course.base.service.core.ConfirmationTokenService;
import ru.gold.ordance.course.base.service.core.MailSenderService;
import ru.gold.ordance.course.persistence.entity.impl.Client;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;

import static ru.gold.ordance.course.persistence.repository.main.EntityRepository.getEntity;

public class EmailSenderWebService implements WebService {
    private final MailSenderService service;
    private final ConfirmationTokenService confirmationTokenService;

    public EmailSenderWebService(MailSenderService service, ConfirmationTokenService confirmationTokenService) {
        this.service = service;
        this.confirmationTokenService = confirmationTokenService;
    }

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

    public ConfirmationToken getByToken(Long clientId, String token) {
        return getEntity(confirmationTokenService.findByClientIdAndToken(clientId, token));
    }
}

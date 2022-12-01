package ru.gold.ordance.course.base.service.core;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.gold.ordance.course.base.service.config.properties.MailSenderProperties;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;

public class MailSenderService {
    private final static String SUBJECT = "Complete registration";
    private final static String TEXT = "To confirm your account, please click here: http://localhost:8090/api/v1/authorizations/confirm-account?token=";

    private final JavaMailSender jms;
    private final String sender;

    public MailSenderService(JavaMailSender jms, MailSenderProperties mailSenderProperties) {
        this.jms = jms;
        this.sender = mailSenderProperties.getSender();
    }

    public void send(ConfirmationToken token) {
        SimpleMailMessage mailMessage = createMessage(token);
        jms.send(mailMessage);
    }

    private SimpleMailMessage createMessage(ConfirmationToken token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(token.getClient().getEmail());
        mailMessage.setSubject(SUBJECT);
        mailMessage.setFrom(sender);
        mailMessage.setText(TEXT + token.getToken());

        return mailMessage;
    }
}

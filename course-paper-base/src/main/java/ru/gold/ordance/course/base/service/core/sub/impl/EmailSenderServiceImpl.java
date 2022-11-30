package ru.gold.ordance.course.base.service.core.sub.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.gold.ordance.course.base.service.core.sub.EmailSenderService;
import ru.gold.ordance.course.persistence.entity.impl.ConfirmationToken;

public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(ConfirmationToken token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(token.getClient().getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("course.paper.asu@mail.ru");
        mailMessage.setText("To confirm your account, please click here: "
                + "http://localhost:8090/api/v1/authorizations/confirm-account?token=" + token.getToken());

        mailSender.send(mailMessage);
    }
}

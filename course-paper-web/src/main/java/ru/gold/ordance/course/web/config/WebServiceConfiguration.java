package ru.gold.ordance.course.web.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gold.ordance.course.base.service.config.ServiceConfiguration;
import ru.gold.ordance.course.base.service.core.*;
import ru.gold.ordance.course.web.service.AuthorizationWebService;
import ru.gold.ordance.course.web.service.EmailSenderWebService;
import ru.gold.ordance.course.web.service.authorization.jwt.JwtProvider;
import ru.gold.ordance.course.web.service.authorization.userdetails.UserDetailsServiceImpl;
import ru.gold.ordance.course.web.service.ClassificationWebService;
import ru.gold.ordance.course.web.service.ClientWebService;
import ru.gold.ordance.course.web.service.FileWebService;
import ru.gold.ordance.course.web.service.HistoryWebService;
import ru.gold.ordance.course.web.service.LanguageWebService;

@Configuration
@ConfigurationPropertiesScan
@Import(ServiceConfiguration.class)
public class WebServiceConfiguration {

    @Bean
    public LanguageWebService languageWebService(LanguageService service) {
        return new LanguageWebService(service);
    }

    @Bean
    public ClassificationWebService classificationWebService(ClassificationService service) {
        return new ClassificationWebService(service);
    }

    @Bean
    public ClientWebService clientWebService(ClientService service) {
        return new ClientWebService(service);
    }

    @Bean
    public FileWebService fileWebService(DocumentService documentService,
                                         LnkDocumentLanguageService lnkService,
                                         LanguageService languageService) {
        return new FileWebService(documentService, lnkService, languageService);
    }

    @Bean
    public HistoryWebService historyWebService(HistoryService service) {
        return new HistoryWebService(service);
    }

    @Bean
    public AuthorizationWebService authorizationWebService(ClientService clientService,
                                                           JwtProvider jwtProvider,
                                                           EmailSenderWebService emailSenderService) {
        return new AuthorizationWebService(clientService, jwtProvider, emailSenderService);
    }

    @Bean
    public EmailSenderWebService emailSenderWebService(MailSenderService mailSenderService,
                                                       ConfirmationTokenService confirmationTokenService) {
        return new EmailSenderWebService(mailSenderService, confirmationTokenService);
    }

    @Bean
    public UserDetailsService userDetailsService(ClientService service) {
        return new UserDetailsServiceImpl(service);
    }
}

package ru.gold.ordance.course.web.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gold.ordance.course.base.service.core.*;
import ru.gold.ordance.course.web.mapper.*;
import ru.gold.ordance.course.web.service.web.authorization.AuthorizationWebService;
import ru.gold.ordance.course.web.service.web.authorization.EmailSenderWebService;
import ru.gold.ordance.course.web.service.web.authorization.impl.AuthorizationWebServiceImpl;
import ru.gold.ordance.course.web.service.web.authorization.impl.EmailSenderWebServiceImpl;
import ru.gold.ordance.course.web.service.web.authorization.jwt.JwtProvider;
import ru.gold.ordance.course.web.service.web.authorization.userdetails.UserDetailsServiceImpl;
import ru.gold.ordance.course.web.service.web.classification.ClassificationWebService;
import ru.gold.ordance.course.web.service.web.classification.ClassificationWebServiceImpl;
import ru.gold.ordance.course.web.service.web.client.ClientWebService;
import ru.gold.ordance.course.web.service.web.client.ClientWebServiceImpl;
import ru.gold.ordance.course.web.service.web.file.FileWebService;
import ru.gold.ordance.course.web.service.web.file.FileWebServiceImpl;
import ru.gold.ordance.course.web.service.web.file.helper.FileDatabaseHelper;
import ru.gold.ordance.course.web.service.web.file.helper.FileSystemHelper;
import ru.gold.ordance.course.web.service.web.history.HistoryWebService;
import ru.gold.ordance.course.web.service.web.history.HistoryWebServiceImpl;
import ru.gold.ordance.course.web.service.web.language.LanguageWebService;
import ru.gold.ordance.course.web.service.web.language.LanguageWebServiceImpl;

@Configuration
@ConfigurationPropertiesScan
public class WebServiceConfiguration {

    @Bean
    public LanguageWebService languageWebService(LanguageService service, LanguageMapper mapper) {
        return new LanguageWebServiceImpl(service, mapper);
    }

    @Bean
    public ClassificationWebService classificationWebService(ClassificationService service, ClassificationMapper mapper, FileWebService fileService) {
        return new ClassificationWebServiceImpl(service, mapper, fileService);
    }

    @Bean
    public ClientWebService clientWebService(ClientService service, ClientMapper mapper) {
        return new ClientWebServiceImpl(service, mapper);
    }

    @Bean
    public FileDatabaseHelper fileDatabaseHelper(DocumentService documentService,
                                                 LnkDocumentLanguageService lnkService,
                                                 LanguageService languageService,
                                                 FileMapper mapper,
                                                 LanguageMapper languageMapper) {
        return new FileDatabaseHelper(documentService, lnkService, languageService, mapper, languageMapper);
    }

    @Bean
    public FileSystemHelper fileSystemHelper(@Value("${spring.servlet.multipart.location}") String storagePath) {
        return new FileSystemHelper(storagePath);
    }

    @Bean
    public FileWebService fileWebService(FileDatabaseHelper databaseHelper, FileSystemHelper systemHelper) {
        return new FileWebServiceImpl(databaseHelper, systemHelper);
    }

    @Bean
    public HistoryWebService historyWebService(HistoryService service, HistoryMapper mapper) {
        return new HistoryWebServiceImpl(service, mapper);
    }

    @Bean
    public AuthorizationWebService authorizationWebService(ClientService clientService,
                                                           ClientMapper mapper,
                                                           JwtProvider jwtProvider,
                                                           EmailSenderWebService emailSenderService) {
        return new AuthorizationWebServiceImpl(clientService, mapper, jwtProvider, emailSenderService);
    }

    @Bean
    public EmailSenderWebService emailSenderWebService(MailSenderService mailSenderService,
                                                       ConfirmationTokenService confirmationTokenService) {
        return new EmailSenderWebServiceImpl(mailSenderService, confirmationTokenService);
    }

    @Bean
    public UserDetailsService userDetailsService(ClientService service) {
        return new UserDetailsServiceImpl(service);
    }
}

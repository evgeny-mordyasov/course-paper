package ru.gold.ordance.course.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.course.base.entity.Client;
import ru.gold.ordance.course.base.service.ClientService;
import ru.gold.ordance.course.web.Application;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignInRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignInResponse;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationTokenLifeRequest;
import ru.gold.ordance.course.web.service.authorization.AuthorizationWebService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class AuthorizationRestControllerTest {
    private final static String ENDPOINT = "/api/v1/authorizations/";
    private final static String SUCCESS = StatusCode.SUCCESS.name();
    private final static String UNAUTHORIZED = StatusCode.UNAUTHORIZED.name();
    private final static String BANNED = StatusCode.BANNED.name();
    private final static String VIOLATES_CONSTRAINT = StatusCode.VIOLATES_CONSTRAINT.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private AuthorizationWebService service;

    @Autowired
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void tokenLife_invalid_incorrectData() throws Exception {
        final String fakeToken = randomString();
        final boolean valid = false;

        AuthorizationTokenLifeRequest rq = new AuthorizationTokenLifeRequest(fakeToken);

        mockMvc.perform(post(ENDPOINT + "token/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.valid", is(valid)));

    }

    @Test
    public void tokenLife_valid() throws Exception {
        final boolean valid = true;

        final String email = randomString();
        final String password = randomString();

        clientService.save(Client.builder()
                        .withSurname(randomString())
                        .withName(randomString())
                        .withPatronymic(randomString())
                        .withEmail(email)
                        .withPassword(password)
                        .build());

        AuthorizationSignInResponse rs = service.signIn(new AuthorizationSignInRequest(email, password));

        AuthorizationTokenLifeRequest rq = new AuthorizationTokenLifeRequest(rs.getToken());

        mockMvc.perform(post(ENDPOINT + "token/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.valid", is(valid)));
    }

    @Test
    public void signIn_correctData() throws Exception {
        final String email = randomString();
        final String password = randomString();

        clientService.save(Client.builder()
                .withSurname(randomString())
                .withName(randomString())
                .withPatronymic(randomString())
                .withEmail(email)
                .withPassword(password)
                .build());

        AuthorizationSignInRequest rq = new AuthorizationSignInRequest(email, password);

        mockMvc.perform(post(ENDPOINT + "sign-in/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));
    }

    @Test
    public void signIn_incorrectData() throws Exception {
        final String fakeEmail = randomString();
        final String fakePassword = randomString();

        AuthorizationSignInRequest rq = new AuthorizationSignInRequest(fakeEmail, fakePassword);

        mockMvc.perform(post(ENDPOINT + "sign-in/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(UNAUTHORIZED)))
                .andExpect(jsonPath("$.status.description", is(StatusCode.UNAUTHORIZED.getErrorMessage())));
    }

    @Test
    public void signIn_banned() throws Exception {
        final String email = randomString();
        final String password = randomString();

        clientService.save(Client.builder()
                .withSurname(randomString())
                .withName(randomString())
                .withPatronymic(randomString())
                .withEmail(email)
                .withPassword(password)
                .build());

        Optional<Client> found = clientService.findByEmail(email);
        assertTrue(found.isPresent());
        clientService.deleteById(found.get().getId());

        AuthorizationSignInRequest rq = new AuthorizationSignInRequest(email, password);

        mockMvc.perform(post(ENDPOINT + "sign-in/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(BANNED)))
                .andExpect(jsonPath("$.status.description", is(StatusCode.BANNED.getErrorMessage())));
    }

    @Test
    public void signUp_success() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();
        final String password = randomString();

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));
    }

    @Test
    public void signUp_violatesConstraint_emailAlreadyExists() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();
        final String password = randomString();

        clientService.save(Client.builder()
                .withSurname(randomString())
                .withName(randomString())
                .withPatronymic(randomString())
                .withEmail(email)
                .withPassword(password)
                .build());

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(VIOLATES_CONSTRAINT)))
                .andExpect(jsonPath("$.status.description", is(StatusCode.VIOLATES_CONSTRAINT.getErrorMessage())));
    }
}

package ru.gold.ordance.course.web.rest.validate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.course.web.TestConfiguration;
import ru.gold.ordance.course.common.api.StatusCode;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignInRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationSignUpRequest;
import ru.gold.ordance.course.web.api.authorization.AuthorizationTokenLifeRequest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.toJSON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@AutoConfigureTestDatabase
@WebAppConfiguration
public class AuthorizationRestControllerValidateTest {
    private final static String ENDPOINT = "/api/v1/authorizations/";
    private final static String INVALID_RQ = StatusCode.INVALID_RQ.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void tokenLife_tokenIsNull() throws Exception {
        final String errorMessage = "The token is null.";

        AuthorizationTokenLifeRequest rq = new AuthorizationTokenLifeRequest(null);

        mockMvc.perform(post(ENDPOINT + "token/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void tokenLife_tokenIsEmpty() throws Exception {
        final String token = "";
        final String errorMessage = "The token is empty.";

        AuthorizationTokenLifeRequest rq = new AuthorizationTokenLifeRequest(token);

        mockMvc.perform(post(ENDPOINT + "token/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signIn_emailIsNull() throws Exception {
        final String password = randomString();
        final String errorMessage = "The email is null.";

        AuthorizationSignInRequest rq = new AuthorizationSignInRequest(null, password);

        mockMvc.perform(post(ENDPOINT + "sign-in/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signIn_emailIsEmpty() throws Exception {
        final String email = "";
        final String password = randomString();
        final String errorMessage = "The email is empty.";

        AuthorizationSignInRequest rq = new AuthorizationSignInRequest(email, password);

        mockMvc.perform(post(ENDPOINT + "sign-in/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signIn_passwordIsNull() throws Exception {
        final String email = randomString();
        final String errorMessage = "The password is null.";

        AuthorizationSignInRequest rq = new AuthorizationSignInRequest(email, null);

        mockMvc.perform(post(ENDPOINT + "sign-in/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signIn_passwordIsEmpty() throws Exception {
        final String email = randomString();
        final String password = "";
        final String errorMessage = "The password is empty.";

        AuthorizationSignInRequest rq = new AuthorizationSignInRequest(email, password);

        mockMvc.perform(post(ENDPOINT + "sign-in/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_surnameIsNull() throws Exception {
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();
        final String password = randomString();
        final String errorMessage = "The surname is null.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(null)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_surnameIsEmpty() throws Exception {
        final String surname = "";
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();
        final String password = randomString();
        final String errorMessage = "The surname is empty.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_nameIsNull() throws Exception {
        final String surname = randomString();
        final String patronymic = randomString();
        final String email = randomString();
        final String password = randomString();
        final String errorMessage = "The name is null.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(null)
                .patronymic(patronymic)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_nameIsEmpty() throws Exception {
        final String surname = randomString();
        final String name = "";
        final String patronymic = randomString();
        final String email = randomString();
        final String password = randomString();
        final String errorMessage = "The name is empty.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_patronymicIsNull() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String email = randomString();
        final String password = randomString();
        final String errorMessage = "The patronymic is null.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(null)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_patronymicIsEmpty() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String patronymic = "";
        final String email = randomString();
        final String password = randomString();
        final String errorMessage = "The patronymic is empty.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_emailIsNull() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String password = randomString();
        final String errorMessage = "The email is null.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .email(null)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_emailIsEmpty() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = "";
        final String password = randomString();
        final String errorMessage = "The email is empty.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_passwordIsNull() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();
        final String errorMessage = "The password is null.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(null)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void signUp_passwordIsEmpty() throws Exception {
        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();
        final String password = "";
        final String errorMessage = "The password is empty.";

        AuthorizationSignUpRequest rq = AuthorizationSignUpRequest.builder()
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .email(email)
                .build();

        mockMvc.perform(post(ENDPOINT + "sign-up/")
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }
}

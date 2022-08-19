package ru.gold.ordance.course.web.rest.validate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.course.web.TestConfiguration;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.api.client.ClientUpdateRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.course.common.utils.TestUtils.generateId;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.toJSON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@AutoConfigureTestDatabase
@WebAppConfiguration
@PropertySource("classpath:application-test.properties")
public class ClientRestControllerValidateTest {
    private final static String ENDPOINT = "/api/v1/clients/";
    private final static String INVALID_RQ = StatusCode.INVALID_RQ.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void findById_idIsNotPositive_invalidRq() throws Exception {
        final long entityId = -1L;
        final String errorMessage = "The entityId is not positive.";

        mockMvc.perform(get(ENDPOINT + entityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void findByEmail_emailIsEmpty_invalidRq() throws Exception {
        final String email = " ";
        final String errorMessage = "The email is empty.";

        mockMvc.perform(get(ENDPOINT + "email/" + email))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_entityIdIsNull_invalidRq() throws Exception {
        final String errorMessage = "The entityId is null.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(null)
                .surname(randomString())
                .name(randomString())
                .patronymic(randomString())
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_entityIdIsNotPositive_invalidRq() throws Exception {
        final Long entityId = -generateId();
        final String errorMessage = "The entityId is not positive.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(entityId)
                .surname(randomString())
                .name(randomString())
                .patronymic(randomString())
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_surnameIsNull_invalidRq() throws Exception {
        final String errorMessage = "The surname is null.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(null)
                .name(randomString())
                .patronymic(randomString())
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_surnameIsEmpty_invalidRq() throws Exception {
        final String surname = "";
        final String errorMessage = "The surname is empty.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(surname)
                .name(randomString())
                .patronymic(randomString())
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_nameIsNull_invalidRq() throws Exception {
        final String errorMessage = "The name is null.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(randomString())
                .name(null)
                .patronymic(randomString())
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_nameIsEmpty_invalidRq() throws Exception {
        final String name = "";
        final String errorMessage = "The name is empty.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(randomString())
                .name(name)
                .patronymic(randomString())
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_patronymicIsNull_invalidRq() throws Exception {
        final String errorMessage = "The patronymic is null.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(randomString())
                .name(randomString())
                .patronymic(null)
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_patronymicIsEmpty_invalidRq() throws Exception {
        final String patronymic = "";
        final String errorMessage = "The patronymic is empty.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(randomString())
                .name(randomString())
                .patronymic(patronymic)
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_passwordIsNull_invalidRq() throws Exception {
        final String errorMessage = "The password is null.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(randomString())
                .name(randomString())
                .patronymic(randomString())
                .password(null)
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_passwordIsEmpty_invalidRq() throws Exception {
        final String password = "";
        final String errorMessage = "The password is empty.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(generateId())
                .surname(randomString())
                .name(randomString())
                .patronymic(randomString())
                .password(password)
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }

    @Test
    public void update_clientDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final String errorMessage = "The entityId was not found in the database.";

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(currentId)
                .surname(randomString())
                .name(randomString())
                .patronymic(randomString())
                .password(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void deleteById_idIsNotPositive_invalidRq() throws Exception {
        final long entityId = -1L;
        final String errorMessage = "The entityId is not positive.";

        mockMvc.perform(delete(ENDPOINT + entityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }
}

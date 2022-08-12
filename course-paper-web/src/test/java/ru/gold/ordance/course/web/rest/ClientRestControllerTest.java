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
import ru.gold.ordance.course.base.entity.Role;
import ru.gold.ordance.course.base.persistence.ClientRepository;
import ru.gold.ordance.course.web.Application;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.api.client.ClientGetByIdRequest;
import ru.gold.ordance.course.web.api.client.ClientGetResponse;
import ru.gold.ordance.course.web.api.client.ClientUpdateRequest;
import ru.gold.ordance.course.web.service.client.ClientWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.rest.utils.RequestUtils.toJSON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class ClientRestControllerTest {
    private final static String ENDPOINT = "/api/v1/clients/";
    private final static String SUCCESS = StatusCode.SUCCESS.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ClientWebService service;
    
    @Autowired
    private ClientRepository repository;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void findAll_noOneHasBeenFound() throws Exception {
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;

        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();

        final int savedClientId = repository.saveAndFlush(Client.builder()
                        .withSurname(surname)
                        .withName(name)
                        .withPatronymic(patronymic)
                        .withEmail(email)
                        .withPassword(randomString())
                        .withRole(Role.USER)
                        .withIsActive(true)
                        .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundOne)))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedClientId)))
                .andExpect(jsonPath("$.list[0].surname", is(surname)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.list[0].patronymic", is(patronymic)))
                .andExpect(jsonPath("$.list[0].email", is(email)))
                .andExpect(jsonPath("$.list[0].role", is(Role.USER.name())))
                .andExpect(jsonPath("$.list[0].active", is(true)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String fSurname = randomString();
        final String fName = randomString();
        final String fPatronymic = randomString();
        final String fEmail = randomString();

        final int firstSavedClientId = repository.saveAndFlush(Client.builder()
                        .withSurname(fSurname)
                        .withName(fName)
                        .withPatronymic(fPatronymic)
                        .withEmail(fEmail)
                        .withPassword(randomString())
                        .withRole(Role.USER)
                        .withIsActive(true)
                        .build())
                .getId()
                .intValue();

        final String sSurname = randomString();
        final String sName = randomString();
        final String sPatronymic = randomString();
        final String sEmail = randomString();

        final int secondSavedClientId = repository.saveAndFlush(Client.builder()
                        .withSurname(sSurname)
                        .withName(sName)
                        .withPatronymic(sPatronymic)
                        .withEmail(sEmail)
                        .withPassword(randomString())
                        .withRole(Role.USER)
                        .withIsActive(true)
                        .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundALot)))
                .andExpect(jsonPath("$.list[0].entityId", oneOf(firstSavedClientId, secondSavedClientId)))
                .andExpect(jsonPath("$.list[0].surname", oneOf(fSurname, sSurname)))
                .andExpect(jsonPath("$.list[0].name", oneOf(fName, sName)))
                .andExpect(jsonPath("$.list[0].patronymic", oneOf(fPatronymic, sPatronymic)))
                .andExpect(jsonPath("$.list[0].email", oneOf(fEmail, sEmail)))
                .andExpect(jsonPath("$.list[0].role", is(Role.USER.name())))
                .andExpect(jsonPath("$.list[0].active", is(true)))
                .andExpect(jsonPath("$.list[1].entityId", oneOf(firstSavedClientId, secondSavedClientId)))
                .andExpect(jsonPath("$.list[1].surname", oneOf(fSurname, sSurname)))
                .andExpect(jsonPath("$.list[1].name", oneOf(fName, sName)))
                .andExpect(jsonPath("$.list[1].patronymic", oneOf(fPatronymic, sPatronymic)))
                .andExpect(jsonPath("$.list[1].email", oneOf(fEmail, sEmail)))
                .andExpect(jsonPath("$.list[1].role", is(Role.USER.name())))
                .andExpect(jsonPath("$.list[1].active", is(true)))
                .andExpect(jsonPath("$.total", is(foundALot)));
    }

    @Test
    public void findById_notFound() throws Exception {
        String fakeId = "999";
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + fakeId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;

        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();

        final int savedClientId = repository.saveAndFlush(Client.builder()
                        .withSurname(surname)
                        .withName(name)
                        .withPatronymic(patronymic)
                        .withEmail(email)
                        .withPassword(randomString())
                        .withRole(Role.USER)
                        .withIsActive(true)
                        .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedClientId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundOne)))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedClientId)))
                .andExpect(jsonPath("$.list[0].surname", is(surname)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.list[0].patronymic", is(patronymic)))
                .andExpect(jsonPath("$.list[0].email", is(email)))
                .andExpect(jsonPath("$.list[0].role", is(Role.USER.name())))
                .andExpect(jsonPath("$.list[0].active", is(true)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByEmail_notFound() throws Exception {
        String fakeEmail = randomString();
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "email/" + fakeEmail))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByEmail_found() throws Exception {
        final int foundOne = 1;

        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String email = randomString();

        final int savedClientId = repository.saveAndFlush(Client.builder()
                        .withSurname(surname)
                        .withName(name)
                        .withPatronymic(patronymic)
                        .withEmail(email)
                        .withPassword(randomString())
                        .withRole(Role.USER)
                        .withIsActive(true)
                        .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "email/" + email))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedClientId)))
                .andExpect(jsonPath("$.list[0].surname", is(surname)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.list[0].patronymic", is(patronymic)))
                .andExpect(jsonPath("$.list[0].email", is(email)))
                .andExpect(jsonPath("$.list[0].role", is(Role.USER.name())))
                .andExpect(jsonPath("$.list[0].active", is(true)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update() throws Exception {
        final Long savedClientId = repository.saveAndFlush(Client.builder()
                        .withSurname(randomString())
                        .withName(randomString())
                        .withPatronymic(randomString())
                        .withEmail(randomString())
                        .withPassword(randomString())
                        .withRole(Role.USER)
                        .withIsActive(true)
                        .build())
                .getId();

        final String surname = randomString();
        final String name = randomString();
        final String patronymic = randomString();
        final String password = randomString();

        ClientUpdateRequest rq = ClientUpdateRequest.builder()
                .entityId(savedClientId)
                .surname(surname)
                .name(name)
                .patronymic(patronymic)
                .password(password)
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.client.entityId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.client.surname", is(surname)))
                .andExpect(jsonPath("$.client.name", is(name)))
                .andExpect(jsonPath("$.client.patronymic", is(patronymic)));
    }

    @Test
    public void deleteById() throws Exception {
        final Long savedClientId = repository.saveAndFlush(Client.builder()
                        .withSurname(randomString())
                        .withName(randomString())
                        .withPatronymic(randomString())
                        .withEmail(randomString())
                        .withRole(Role.USER)
                        .withIsActive(true)
                        .build())
                .getId();

        mockMvc.perform(delete(ENDPOINT + savedClientId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        ClientGetResponse rs = service.findById(new ClientGetByIdRequest(savedClientId));

        assertEquals(1, rs.getList().size());
        assertFalse(rs.getList().get(0).isActive());
    }
}

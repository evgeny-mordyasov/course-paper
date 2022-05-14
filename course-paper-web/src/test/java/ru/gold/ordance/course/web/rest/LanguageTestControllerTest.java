package ru.gold.ordance.course.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.persistence.LanguageRepository;
import ru.gold.ordance.course.web.Application;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.api.language.*;
import ru.gold.ordance.course.web.service.language.LanguageWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class LanguageTestControllerTest {
    private final static String ENDPOINT = "/api/v1/languages/";
    private final static String SUCCESS = StatusCode.SUCCESS.name();
    private final static String INVALID_RQ = StatusCode.INVALID_RQ.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private LanguageWebService service;

    @Autowired
    private LanguageRepository repository;

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

        final String name = randomString();
        final int savedLanguageId = repository.saveAndFlush(Language.builder()
                .withName(name)
                .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundOne)))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedLanguageId)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstName = randomString();
        final int firstSavedLanguageId = repository.saveAndFlush(Language.builder()
                .withName(firstName)
                .build())
                .getId()
                .intValue();


        final String secondName = randomString();
        final int secondSavedLanguageId = repository.saveAndFlush(Language.builder()
                .withName(secondName)
                .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundALot)))
                .andExpect(jsonPath("$.list[0].entityId", oneOf(firstSavedLanguageId, secondSavedLanguageId)))
                .andExpect(jsonPath("$.list[0].name", oneOf(firstName, secondName)))
                .andExpect(jsonPath("$.list[1].entityId", oneOf(firstSavedLanguageId, secondSavedLanguageId)))
                .andExpect(jsonPath("$.list[1].name", oneOf(firstName, secondName)))
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

        final String name = randomString();
        final int savedLanguageId = repository.saveAndFlush(Language.builder()
                .withName(name)
                .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedLanguageId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundOne)))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedLanguageId)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByName_notFound() throws Exception {
        String fakeName = randomString();
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "name/" + fakeName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;

        final String name = randomString();
        final int savedLanguageId = repository.saveAndFlush(Language.builder()
                .withName(name)
                .build())
                .getId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + name))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedLanguageId)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void save() throws Exception {
        final String name = randomString();
        LanguageSaveRequest rq = new LanguageSaveRequest(name);

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        LanguageGetResponse rs = service.findByName(new LanguageGetByNameRequest(name));

        assertEquals(1, rs.getList().size());
        assertEquals(name, rs.getList().get(0).getName());
    }

    @Test
    public void update() throws Exception {
        final Long savedLanguageId = repository.saveAndFlush(Language.builder()
                .withName(randomString())
                .build())
                .getId();

        final String name = randomString();
        LanguageUpdateRequest rq = LanguageUpdateRequest.builder()
                .entityId(savedLanguageId)
                .name(name)
                .build();

        mockMvc.perform(put(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        LanguageGetResponse rs = service.findById(new LanguageGetByIdRequest(savedLanguageId));

        assertEquals(1, rs.getList().size());
        assertEquals(name, rs.getList().get(0).getName());
    }

    @Test
    public void update_clientDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final String errorMessage = "The language by id not found.";

        LanguageUpdateRequest rq = LanguageUpdateRequest.builder()
                .entityId(currentId)
                .name(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void deleteById() throws Exception {
        final Long savedLanguageId = repository.saveAndFlush(Language.builder()
                .withName(randomString())
                .build())
                .getId();

        mockMvc.perform(delete(ENDPOINT + savedLanguageId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        LanguageGetResponse rs = service.findById(new LanguageGetByIdRequest(savedLanguageId));

        assertEquals(0, rs.getList().size());
    }
}

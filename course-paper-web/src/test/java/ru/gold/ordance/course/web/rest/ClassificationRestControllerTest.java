package ru.gold.ordance.course.web.rest;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.persistence.repository.ClassificationRepository;
import ru.gold.ordance.course.web.TestConfiguration;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.service.web.classification.ClassificationWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.toJSON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@PropertySource("classpath:application-test.properties")
public class ClassificationRestControllerTest {
    private final static String ENDPOINT = "/api/v1/classifications/";
    private final static String SUCCESS = StatusCode.SUCCESS.name();
    private final static String VIOLATES_CONSTRAINT = StatusCode.VIOLATES_CONSTRAINT.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ClassificationWebService service;

    @Autowired
    private ClassificationRepository repository;

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
        final int savedClassificationId = repository.preserve(Classification.builder()
                .withName(name)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundOne)))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedClassificationId)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstName = randomString();
        final int firstSavedClassificationId = repository.preserve(Classification.builder()
                .withName(firstName)
                .build())
                .getEntityId()
                .intValue();


        final String secondName = randomString();
        final int secondSavedClassificationId = repository.preserve(Classification.builder()
                .withName(secondName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundALot)))
                .andExpect(jsonPath("$.list[0].entityId", oneOf(firstSavedClassificationId, secondSavedClassificationId)))
                .andExpect(jsonPath("$.list[0].name", oneOf(firstName, secondName)))
                .andExpect(jsonPath("$.list[1].entityId", oneOf(firstSavedClassificationId, secondSavedClassificationId)))
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
        final int savedClassificationId = repository.preserve(Classification.builder()
                .withName(name)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedClassificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundOne)))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedClassificationId)))
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
        final int savedClassificationId = repository.preserve(Classification.builder()
                .withName(name)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + name))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list[0].entityId", equalTo(savedClassificationId)))
                .andExpect(jsonPath("$.list[0].name", is(name)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void save() throws Exception {
        final String name = randomString();
        ClassificationSaveRequest rq = new ClassificationSaveRequest(name);

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.classification.entityId", notNullValue()))
                .andExpect(jsonPath("$.classification.name", is(name)));
    }

//    @Test
//    public void save_nameAlreadyExists() throws Exception {
//        final String name = randomString();
//        ClassificationSaveRequest rq = new ClassificationSaveRequest(name);
//
//        service.save(rq);
//
//        mockMvc.perform(post(ENDPOINT)
//                .content(toJSON(rq))
//                .contentType(JSON))
//                .andExpect(content().contentType(JSON))
//                .andExpect(jsonPath("$.status.code", is(VIOLATES_CONSTRAINT)))
//                .andExpect(jsonPath("$.status.description", is(StatusCode.VIOLATES_CONSTRAINT.getErrorMessage())));
//    }

    @Test
    public void update() throws Exception {
        final Long savedClassificationId = repository.preserve(Classification.builder()
                .withName(randomString())
                .build())
                .getEntityId();

        final String name = randomString();
        ClassificationUpdateRequest rq = ClassificationUpdateRequest.builder()
                .entityId(savedClassificationId)
                .name(name)
                .build();

        mockMvc.perform(put(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.classification.entityId", equalTo(savedClassificationId.intValue())))
                .andExpect(jsonPath("$.classification.name", is(name)));
    }

    @Test
    public void deleteById() throws Exception {
        final Long savedClassificationId = repository.preserve(Classification.builder()
                .withName(randomString())
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedClassificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        ClassificationGetEntityResponse rs = service.findById(new ClassificationGetByIdRequest(savedClassificationId));

        assertNull(rs.getClassification());
    }
}

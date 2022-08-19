package ru.gold.ordance.course.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.entity.Language;
import ru.gold.ordance.course.base.persistence.repository.ClassificationRepository;
import ru.gold.ordance.course.base.persistence.repository.LanguageRepository;
import ru.gold.ordance.course.web.TestConfiguration;
import ru.gold.ordance.course.web.api.StatusCode;
import ru.gold.ordance.course.web.api.file.FileDeleteByUrnRequest;
import ru.gold.ordance.course.web.api.file.FileSaveRequest;
import ru.gold.ordance.course.web.service.web.file.FileWebService;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.course.common.utils.TestUtils.randomFullFileName;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.course.web.utils.RequestUtils.toJSON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@PropertySource("classpath:application-test.properties")
public class FileRestControllerTest {
    private final static String ENDPOINT = "/api/v1/files/";
    private final static String SUCCESS = StatusCode.SUCCESS.name();
    private final static String VIOLATES_CONSTRAINT = StatusCode.VIOLATES_CONSTRAINT.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private FileWebService service;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    private Language language;

    private Classification classification;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        language = languageRepository.saveAndFlush(Language.builder()
                .withName(randomString())
                .build());

        classification = classificationRepository.saveAndFlush(Classification.builder()
                .withName(randomString())
                .build());
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

        final String fileName = randomFullFileName();
        final String urn = saveFile(fileName);

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundOne)))
                .andExpect(jsonPath("$.list[0].language.id", equalTo(language.getId().intValue())))
                .andExpect(jsonPath("$.list[0].document.classification.id", equalTo(classification.getId().intValue())))
                .andExpect(jsonPath("$.list[0].urn", is(urn)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstFileName = randomFullFileName();
        final String firstUrn = saveFile(firstFileName);

        final String secondFileName = randomFullFileName();
        final String secondUrn = saveFile(secondFileName);

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.list", hasSize(foundALot)))
                .andExpect(jsonPath("$.list[0].language.id", equalTo(language.getId().intValue())))
                .andExpect(jsonPath("$.list[0].document.classification.id", equalTo(classification.getId().intValue())))
                .andExpect(jsonPath("$.list[0].urn", oneOf(firstUrn, secondUrn)))
                .andExpect(jsonPath("$.list[1].language.id", equalTo(language.getId().intValue())))
                .andExpect(jsonPath("$.list[1].document.classification.id", equalTo(classification.getId().intValue())))
                .andExpect(jsonPath("$.list[1].urn", oneOf(firstUrn, secondUrn)))
                .andExpect(jsonPath("$.total", is(foundALot)));
    }

    private String saveFile(String fileName) throws IOException {
        service.save(FileSaveRequest.builder()
                .file(createFile(fileName))
                .languageId(language.getId())
                .classificationId(classification.getId())
                .build());

        return getUrn(fileName);
    }

    private MockMultipartFile createFile(String fileName) {
        return new MockMultipartFile(
                "file",
                fileName,
                MediaType.TEXT_PLAIN_VALUE,
                randomString().getBytes()
        );
    }

    private String getUrn(String fileName) {
        return String.format("/%s/%s/%s", classification.getName(), language.getName(), fileName);
    }

    @Test
    public void save() throws Exception {
        final String filename = randomFullFileName();
        final String urn = getUrn(filename);

        mockMvc.perform(multipart(ENDPOINT)
                .file(createFile(filename))
                .param("languageId", String.valueOf(language.getId()))
                .param("classificationId", String.valueOf(classification.getId())))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.file.urn", is(urn)))
                .andExpect(jsonPath("$.file.language.id", equalTo(language.getId().intValue())))
                .andExpect(jsonPath("$.file.document.classification.id", equalTo(classification.getId().intValue())));
    }

//    @Test
//    public void save_urnAlreadyExists() throws Exception {
//        final String filename = randomFullFileName();
//        saveFile(filename);
//
//        mockMvc.perform(multipart(ENDPOINT)
//                .file(createFile(filename))
//                .param("languageId", String.valueOf(language.getId()))
//                .param("classificationId", String.valueOf(classification.getId())))
//                .andExpect(content().contentType(JSON))
//                .andExpect(jsonPath("$.status.code", is(VIOLATES_CONSTRAINT)))
//                .andExpect(jsonPath("$.status.description", is(StatusCode.VIOLATES_CONSTRAINT.getErrorMessage())));
//    }

    @Test
    public void deleteByUrn_notExist() throws Exception {
        final String fakeUrn = randomString();
        FileDeleteByUrnRequest rq = new FileDeleteByUrnRequest(fakeUrn);

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));
    }
}

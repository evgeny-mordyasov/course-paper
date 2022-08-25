package ru.gold.ordance.course.web.rest.validate;

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
import ru.gold.ordance.course.common.api.StatusCode;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.course.common.utils.TestUtils.randomFullFileName;
import static ru.gold.ordance.course.common.utils.TestUtils.randomString;
import static ru.gold.ordance.course.web.utils.RequestUtils.JSON;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@PropertySource("classpath:application-test.properties")
public class FileRestControllerValidateTest {
    private final static String ENDPOINT = "/api/v1/files/";
    private final static String INVALID_RQ = StatusCode.INVALID_RQ.name();

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void save_classificationIdIsNotPositive_invalidRq() throws Exception {
        final String languageId =  saveLanguage();
        final String classificationId = "-1";
        final String errorMessage = "The classificationId is not positive.";

        mockMvc.perform(multipart(ENDPOINT)
                .file(createFile())
                .param("languageId", languageId)
                .param("classificationId", classificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void save_classificationIdNotFoundInDatabase_invalidRq() throws Exception {
        final String languageId = saveLanguage();
        final String classificationId = "999";
        final String errorMessage = "The classificationId was not found in the database.";

        mockMvc.perform(multipart(ENDPOINT)
                .file(createFile())
                .param("languageId", languageId)
                .param("classificationId", classificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void save_languageIdIsNotPositive_invalidRq() throws Exception {
        final String languageId = "-1";
        final String classificationId = saveClassification();
        final String errorMessage = "The languageId is not positive.";

        mockMvc.perform(multipart(ENDPOINT)
                .file(createFile())
                .param("languageId", languageId)
                .param("classificationId", classificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void save_languageIdNotFoundInDatabase_invalidRq() throws Exception {
        final String languageId = "999";
        final String classificationId = saveClassification();
        final String errorMessage = "The languageId was not found in the database.";

        mockMvc.perform(multipart(ENDPOINT)
                .file(createFile())
                .param("languageId", languageId)
                .param("classificationId", classificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void save_fileIsEmpty_invalidRq() throws Exception {
        final String languageId = "1";
        final String classificationId = "1";
        final String errorMessage = "The file is missing.";

        mockMvc.perform(multipart(ENDPOINT)
                .file(createEmptyFile())
                .param("languageId", languageId)
                .param("classificationId", classificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void save_fileNameIsEmpty_invalidRq() throws Exception {
        final String languageId = "1";
        final String classificationId = "1";
        final String errorMessage = "The fileName is empty.";

        mockMvc.perform(multipart(ENDPOINT)
                .file(createFileWithEmptyFileName())
                .param("languageId", languageId)
                .param("classificationId", classificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    @Test
    public void save_fileExtensionNotSupported_invalidRq() throws Exception {
        final String languageId = saveLanguage();
        final String classificationId = saveClassification();
        final String errorMessage = "The file extension not supported.";

        mockMvc.perform(multipart(ENDPOINT)
                .file(createFileWithInvalidExtension())
                .param("languageId", languageId)
                .param("classificationId", classificationId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", is(errorMessage)));
    }

    private MockMultipartFile createFile() {
        return new MockMultipartFile(
                "file",
                randomFullFileName(),
                MediaType.TEXT_PLAIN_VALUE,
                randomString().getBytes()
        );
    }

    private MockMultipartFile createEmptyFile() {
        return new MockMultipartFile(
                "file",
                randomFullFileName(),
                MediaType.TEXT_PLAIN_VALUE,
                new byte[]{}
        );
    }

    private MockMultipartFile createFileWithEmptyFileName() {
        return new MockMultipartFile(
                "file",
                "",
                MediaType.TEXT_PLAIN_VALUE,
                randomString().getBytes()
        );
    }

    private MockMultipartFile createFileWithInvalidExtension() {
        return new MockMultipartFile(
                "file",
                randomString() + "." + randomString(),
                MediaType.TEXT_PLAIN_VALUE,
                randomString().getBytes()
        );
    }

    private String saveLanguage() {
        return String.valueOf(languageRepository.saveAndFlush(Language.builder()
                .withName(randomString())
                .build())
                .getEntityId());
    }

    private String saveClassification() {
        return String.valueOf(classificationRepository.saveAndFlush(Classification.builder()
                .withName(randomString())
                .build())
                .getEntityId());
    }
}

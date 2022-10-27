package ru.gold.ordance.course.web.api.file;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.SaveRequest;

import static ru.gold.ordance.course.common.utils.FileUtils.getFileExtension;
import static ru.gold.ordance.course.web.service.web.file.FileExtension.isFromWhitelist;
import static ru.gold.ordance.course.web.utils.ValidatorUtils.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class FilePatchRequest implements SaveRequest {
    private static final long serialVersionUID = 1L;

    private final MultipartFile file;
    private final Long documentId;
    private final Long languageId;

    @Override
    public void validate() {
        errorObjectId(getDocumentId(), "classificationId");
        errorObjectId(getLanguageId(), "languageId");
        errorTrue(getFile().isEmpty(), "The file is missing.");
        errorString(getFile().getOriginalFilename(), "fileName");
        errorFalse(isFromWhitelist(getFileExtension(getFile().getOriginalFilename())), "The file extension not supported.");
        errorFalse(getFile().getSize() <= 1024 * 1024 * 5, "The file size exceeds 5 MB.");
    }
}

package ru.gold.ordance.course.internal.api.domain.file.request;

import lombok.*;
import ru.gold.ordance.course.internal.api.domain.Request;
import ru.gold.ordance.course.internal.api.dto.CustomMultipartFile;

import static ru.gold.ordance.course.common.constants.FileExtension.isFromWhitelist;
import static ru.gold.ordance.course.common.utils.FileUtils.getFileExtension;
import static ru.gold.ordance.course.internal.api.utils.ValidatorUtils.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class FilePatchRequest implements Request {
    private static final long serialVersionUID = 1L;

    private final CustomMultipartFile file;
    private final Long documentId;
    private final Long languageId;

    @Override
    public void validate() {
        errorObjectId(getDocumentId(), "classificationId");
        errorObjectId(getLanguageId(), "languageId");
        errorTrue(getFile().isEmpty(), "The file is missing.");
        errorString(getFile().getFullFileName(), "fileName");
        errorFalse(isFromWhitelist(getFile().getExtension()), "The file extension not supported.");
        errorFalse(getFile().getSize() <= 1024 * 1024 * 5, "The file size exceeds 5 MB.");
    }
}


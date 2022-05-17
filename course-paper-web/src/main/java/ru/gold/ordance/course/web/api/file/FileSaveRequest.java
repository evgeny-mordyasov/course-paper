package ru.gold.ordance.course.web.api.file;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.course.web.api.SaveRequest;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class FileSaveRequest implements SaveRequest {
    private static final long serialVersionUID = 1L;

    private final MultipartFile file;

    private final Long classificationId;

    private final Long languageId;
}

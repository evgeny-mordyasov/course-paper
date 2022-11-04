package ru.gold.ordance.course.web.api.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.course.web.api.DeleteByIdRequest;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class FileDeleteByIdRequest implements DeleteByIdRequest {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}

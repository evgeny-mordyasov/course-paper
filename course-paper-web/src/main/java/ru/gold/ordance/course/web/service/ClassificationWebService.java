package ru.gold.ordance.course.web.service;

import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.service.core.ClassificationService;
import ru.gold.ordance.course.internal.api.domain.EmptySuccessfulResponse;
import ru.gold.ordance.course.internal.api.domain.classification.request.*;
import ru.gold.ordance.course.internal.api.domain.classification.response.*;
import ru.gold.ordance.course.persistence.entity.impl.Classification;
import ru.gold.ordance.course.internal.api.domain.Response;
import ru.gold.ordance.course.web.mapper.ClassificationMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassificationWebService implements WebService {
    private final ClassificationService service;
    private final ClassificationMapper mapper;

    public ClassificationWebService(ClassificationService service) {
        this.service = service;
        this.mapper = ClassificationMapper.instance();
    }

    public ClassificationGetListResponse findAll() {
        List<Classification> allClassifications = service.findAll();

        return ClassificationGetListResponse.success(
                allClassifications.stream()
                    .map(mapper::fromClassification)
                    .collect(Collectors.toList()));
    }

    public Response findById(ClassificationGetByIdRequest rq) {
        Optional<Classification> foundClassification = service.findByEntityId(rq.getEntityId());

        return process(foundClassification);
    }

    public Response findByName(ClassificationGetByNameRequest rq) {
        Optional<Classification> foundClassification = service.findByName(rq.getName());

        return process(foundClassification);
    }

    public ClassificationSaveResponse save(ClassificationSaveRequest rq) {
        Classification savedClassification = service.save(mapper.toClassification(rq));

        return ClassificationSaveResponse.success(mapper.fromClassification(savedClassification));
    }

    public ClassificationUpdateResponse update(ClassificationUpdateRequest rq) {
        Classification updatedClassification = service.update(mapper.toClassification(rq));

        return ClassificationUpdateResponse.success(mapper.fromClassification(updatedClassification));
    }

    @Transactional
    public ClassificationDeleteResponse deleteById(ClassificationDeleteByIdRequest rq) {
        service.deleteByEntityId(rq.getEntityId());

        return ClassificationDeleteResponse.success();
    }

    private Response process(Optional<Classification> classification) {
        return classification.isPresent()
                ? ClassificationGetEntityResponse.success(mapper.fromClassification(classification.get()))
                : new EmptySuccessfulResponse();
    }
}

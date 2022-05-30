package ru.gold.ordance.course.web.service.classification;

import org.springframework.stereotype.Service;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.service.ClassificationService;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.service.mapper.ClassificationMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassificationWebServiceImpl implements ClassificationWebService {
    private final ClassificationService service;

    private final ClassificationMapper mapper;

    public ClassificationWebServiceImpl(ClassificationService service, ClassificationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ClassificationGetResponse findAll() {
        List<Classification> allClassifications = service.findAll();

        return ClassificationGetResponse.success(
                allClassifications.stream()
                    .map(mapper::fromClassification)
                    .collect(Collectors.toList()));
    }

    @Override
    public ClassificationGetResponse findById(ClassificationGetByIdRequest rq) {
        Optional<Classification> foundClassification = service.findById(rq.getEntityId());

        return search(foundClassification);
    }

    @Override
    public ClassificationGetResponse findByName(ClassificationGetByNameRequest rq) {
        Optional<Classification> foundClassification = service.findByName(rq.getName());

        return search(foundClassification);
    }

    @Override
    public ClassificationSaveResponse save(ClassificationSaveRequest rq) {
        Long entityId = service.save(mapper.toClassification(rq)).getId();

        return ClassificationSaveResponse.success(entityId);
    }

    @Override
    public ClassificationUpdateResponse update(ClassificationUpdateRequest rq) {
        service.update(mapper.toClassification(rq));

        return ClassificationUpdateResponse.success();
    }

    @Override
    public ClassificationDeleteResponse deleteById(ClassificationDeleteByIdRequest rq) {
        service.deleteById(rq.getEntityId());

        return ClassificationDeleteResponse.success();
    }

    private ClassificationGetResponse search(Optional<Classification> classification) {
        return ClassificationGetResponse.success(classification.isEmpty()
                ? Collections.emptyList()
                : Collections.singletonList(mapper.fromClassification(classification.get())));
    }
}

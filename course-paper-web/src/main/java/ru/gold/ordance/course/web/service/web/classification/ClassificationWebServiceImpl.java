package ru.gold.ordance.course.web.service.web.classification;

import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.service.ClassificationService;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.mapper.ClassificationMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ClassificationWebServiceImpl implements ClassificationWebService {
    private final ClassificationService service;
    private final ClassificationMapper mapper;

    public ClassificationWebServiceImpl(ClassificationService service, ClassificationMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public ClassificationGetListResponse findAll() {
        List<Classification> allClassifications = service.findAll();

        return ClassificationGetListResponse.success(
                allClassifications.stream()
                    .map(mapper::fromClassification)
                    .collect(Collectors.toList()));
    }

    @Override
    public ClassificationGetEntityResponse findById(ClassificationGetByIdRequest rq) {
        Classification foundClassification = service.findByEntityId(rq.getEntityId());

        return search(foundClassification);
    }

    @Override
    public ClassificationGetEntityResponse findByName(ClassificationGetByNameRequest rq) {
        Classification foundClassification = service.findByName(rq.getName());

        return search(foundClassification);
    }

    @Override
    public ClassificationSaveResponse save(ClassificationSaveRequest rq) {
        Classification savedClassification = service.save(mapper.toClassification(rq));

        return ClassificationSaveResponse.success(mapper.fromClassification(savedClassification));
    }

    @Override
    public ClassificationUpdateResponse update(ClassificationUpdateRequest rq) {
        Classification updatedClassification = service.update(mapper.toClassification(rq));

        return ClassificationUpdateResponse.success(mapper.fromClassification(updatedClassification));
    }

    @Override
    public ClassificationDeleteResponse deleteById(ClassificationDeleteByIdRequest rq) {
        service.deleteByEntityId(rq.getEntityId());

        return ClassificationDeleteResponse.success();
    }

    private ClassificationGetEntityResponse search(Classification classification) {
        return ClassificationGetEntityResponse.success(mapper.fromClassification(classification));
    }
}

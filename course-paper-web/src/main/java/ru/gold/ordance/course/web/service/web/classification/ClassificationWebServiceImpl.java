package ru.gold.ordance.course.web.service.web.classification;

import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.persistence.entity.Classification;
import ru.gold.ordance.course.base.service.core.sub.ClassificationService;
import ru.gold.ordance.course.web.api.EmptyResponse;
import ru.gold.ordance.course.web.api.Response;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.mapper.ClassificationMapper;
import ru.gold.ordance.course.web.service.web.file.FileWebService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassificationWebServiceImpl implements ClassificationWebService {
    private final ClassificationService service;
    private final ClassificationMapper mapper;
    private final FileWebService fileService;

    public ClassificationWebServiceImpl(ClassificationService service, ClassificationMapper mapper, FileWebService fileService) {
        this.service = service;
        this.mapper = mapper;
        this.fileService = fileService;
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
    public Response findById(ClassificationGetByIdRequest rq) {
        Optional<Classification> foundClassification = service.findByEntityId(rq.getEntityId());

        return process(foundClassification);
    }

    @Override
    public Response findByName(ClassificationGetByNameRequest rq) {
        Optional<Classification> foundClassification = service.findByName(rq.getName());

        return process(foundClassification);
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
    @Transactional
    public ClassificationDeleteResponse deleteById(ClassificationDeleteByIdRequest rq) {
        List<String> urns = fileService.getFilesByClassificationId(rq.getEntityId());

        service.deleteByEntityId(rq.getEntityId());
        fileService.deleteSystemFiles(urns);

        return ClassificationDeleteResponse.success();
    }

    private Response process(Optional<Classification> classification) {
        return classification.isPresent()
                ? ClassificationGetEntityResponse.success(mapper.fromClassification(classification.get()))
                : new EmptyResponse();
    }
}

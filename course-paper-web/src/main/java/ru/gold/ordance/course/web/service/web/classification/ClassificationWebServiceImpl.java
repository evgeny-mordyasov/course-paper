package ru.gold.ordance.course.web.service.web.classification;

import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.course.base.entity.Classification;
import ru.gold.ordance.course.base.service.core.sub.ClassificationService;
import ru.gold.ordance.course.web.api.classification.*;
import ru.gold.ordance.course.web.mapper.ClassificationMapper;
import ru.gold.ordance.course.web.service.web.file.FileWebService;

import java.util.List;
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
    public ClassificationGetEntityResponse findById(ClassificationGetByIdRequest rq) {
        Classification foundClassification = service.getByEntityId(rq.getEntityId());

        return search(foundClassification);
    }

    @Override
    public ClassificationGetEntityResponse findByName(ClassificationGetByNameRequest rq) {
        Classification foundClassification = service.getByName(rq.getName());

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
    @Transactional
    public ClassificationDeleteResponse deleteById(ClassificationDeleteByIdRequest rq) {
        List<String> urns = fileService.getFilesByClassificationId(rq.getEntityId());

        service.deleteByEntityId(rq.getEntityId());
        fileService.deleteSystemFiles(urns);

        return ClassificationDeleteResponse.success();
    }

    private ClassificationGetEntityResponse search(Classification classification) {
        return ClassificationGetEntityResponse.success(mapper.fromClassification(classification));
    }
}

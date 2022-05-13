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
        List<Classification> list = service.findAll();

        if (list.isEmpty()) {
            return ClassificationGetResponse.success(Collections.emptyList());
        } else {
            List<WebClassification> webClients = list.stream()
                    .map(mapper::fromClassification)
                    .collect(Collectors.toList());

            return ClassificationGetResponse.success(webClients);
        }
    }

    @Override
    public ClassificationGetResponse findById(ClassificationGetByIdRequest rq) {
        Optional<Classification> found = service.findById(rq.getEntityId());

        return search(found);
    }

    @Override
    public ClassificationGetResponse findByName(ClassificationGetByNameRequest rq) {
        Optional<Classification> found = service.findByName(rq.getName());

        return search(found);
    }

    @Override
    public ClassificationSaveResponse save(ClassificationSaveRequest rq) {
        service.save(mapper.toClassification(rq));

        return ClassificationSaveResponse.success();
    }

    @Override
    public ClassificationUpdateResponse update(ClassificationUpdateRequest rq) {
        service.update(mapper.toClassification(rq));

        return ClassificationUpdateResponse.success();
    }

    @Override
    public ClassificationDeleteByIdResponse deleteById(ClassificationDeleteByIdRequest rq) {
        service.deleteById(rq.getEntityId());

        return ClassificationDeleteByIdResponse.success();
    }

    private ClassificationGetResponse search(Optional<Classification> found) {
        if (found.isEmpty()) {
            return ClassificationGetResponse.success(Collections.emptyList());
        } else {
            return ClassificationGetResponse.success(Collections.singletonList(mapper.fromClassification(found.get())));
        }
    }
}

package ru.gold.ordance.course.persistence.entity.impl;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.gold.ordance.course.persistence.entity.AbstractEntity;
import ru.gold.ordance.course.persistence.entity.ContainingInternalEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DOCUMENT")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class Document implements AbstractEntity, ContainingInternalEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ENTITY_ID")
    @GeneratedValue(generator = "document_sequence-generator")
    @GenericGenerator(
            name = "document_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "document_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long entityId;

    @Column(name = "FULL_NAME")
    private final String fullName;

    @Column(name = "NAME")
    private final String name;

    @Column(name = "EXTENSION")
    private final String extension;

    @ManyToOne
    @JoinColumn(name = "CLASSIFICATION_ID")
    private final Classification classification;

    @Override
    public List<AbstractEntity> getInternalEntities() {
        return List.of(classification);
    }
}

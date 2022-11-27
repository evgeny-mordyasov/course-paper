package ru.gold.ordance.course.persistence.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "CLASSIFICATION")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class Classification implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ENTITY_ID")
    @GeneratedValue(generator = "classification_sequence-generator")
    @GenericGenerator(
            name = "classification_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "classification_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long entityId;

    @Column(name = "NAME")
    private final String name;
}

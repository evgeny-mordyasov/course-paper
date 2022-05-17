package ru.gold.ordance.course.base.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}) })
public class Classification implements AbstractEntity {
    @Id
    @GeneratedValue(generator = "classification_sequence-generator")
    @GenericGenerator(
            name = "classification_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "classification_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long id;

    private final String name;

    public static Classification create(Long id) {
       return Classification.builder()
               .withId(id)
               .build();
    }
}

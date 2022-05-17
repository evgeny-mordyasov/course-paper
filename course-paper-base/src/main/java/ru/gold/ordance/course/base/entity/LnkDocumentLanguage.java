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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"urn"}) })
public class LnkDocumentLanguage implements AbstractEntity {
    @Id
    @GeneratedValue(generator = "lnk_sequence-generator")
    @GenericGenerator(
            name = "lnk_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "lnk_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long id;

    @ManyToOne
    private final Document document;

    @ManyToOne
    private final Language language;

    private final String urn;
}

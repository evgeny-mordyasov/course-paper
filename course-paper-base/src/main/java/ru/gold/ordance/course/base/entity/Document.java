package ru.gold.ordance.course.base.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class Document implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "document_sequence-generator")
    @GenericGenerator(
            name = "document_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "document_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private final Long id;

    private final String name;

    @ManyToOne
    private final Classification classification;

    @ToString.Exclude
    @OneToMany(cascade = { CascadeType.REMOVE }, mappedBy = "document")
    private Set<LnkDocumentLanguage> lnkDocumentLanguages;
}

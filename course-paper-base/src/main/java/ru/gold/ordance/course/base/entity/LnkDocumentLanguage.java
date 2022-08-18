package ru.gold.ordance.course.base.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENT_LANGUAGE")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true, setterPrefix = "with")
@ToString
public class LnkDocumentLanguage implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
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
    @JoinColumn(name = "DOCUMENT_ID")
    private final Document document;

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private final Language language;

    @Column(name = "URN")
    private final String urn;
}

package ru.vk.sladkiipirojok.questionnairerest.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "poll")
public final class Poll {
    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity",
            strategy = "ru.vk.sladkiipirojok.questionnairerest.generator.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    private Long id;

    private String name;

    private LocalDate beginDate;

    private LocalDate endDate;

    private boolean active;

    @OneToMany(mappedBy = "poll", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Question> questions;
}

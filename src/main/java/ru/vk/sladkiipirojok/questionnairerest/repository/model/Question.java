package ru.vk.sladkiipirojok.questionnairerest.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "question")
public final class Question {
    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity",
            strategy = "ru.vk.sladkiipirojok.questionnairerest.generator.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    private Long id;

    private String text;

    @Column(name = "question_order")
    private int order;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Poll poll;
}

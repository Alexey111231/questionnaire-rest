package ru.vk.sladkiipirojok.questionnairerest.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@Data
public final class PollDTO {
    @NotBlank(message = "Empty name")
    private String name;

    @NotNull(message = "Not beginDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "No past")
    private LocalDate beginDate;

    @NotNull(message = "Not endDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "no active")
    private Boolean active;

    @NotEmpty(message = "No questions")
    @NotNull
    @Valid
    private Set<QuestionDTO> questions;
}

package ru.vk.sladkiipirojok.questionnairerest.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public final class QuestionDTO {
    @NotBlank(message = "No text")
    private String text;
    @NotNull(message = "No order")
    @PositiveOrZero
    private Integer order;
}

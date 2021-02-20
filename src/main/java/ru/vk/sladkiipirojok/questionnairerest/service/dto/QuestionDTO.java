package ru.vk.sladkiipirojok.questionnairerest.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class QuestionDTO {
    @NotBlank(message = "No text")
    private String text;
    @NotNull(message = "No order")
    private Integer order;
}

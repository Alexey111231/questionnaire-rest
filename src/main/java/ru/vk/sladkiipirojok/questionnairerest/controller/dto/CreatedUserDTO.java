package ru.vk.sladkiipirojok.questionnairerest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
public class CreatedUserDTO {
    @PositiveOrZero
    private long id;
}

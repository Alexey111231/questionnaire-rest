package ru.vk.sladkiipirojok.questionnairerest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
public class PostDTO {
    @PositiveOrZero
    private long id;
}

package ru.vk.sladkiipirojok.questionnairerest.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class PageDTO {
    @NotNull
    private List<PollDTO> polls;
}

package ru.vk.sladkiipirojok.questionnairerest.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PageDTO {
    @NotNull
    private final List<PollDTO> polls;
}

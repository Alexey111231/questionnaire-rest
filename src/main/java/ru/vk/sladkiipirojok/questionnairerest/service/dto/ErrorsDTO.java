package ru.vk.sladkiipirojok.questionnairerest.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorsDTO {
    @NotEmpty
    private List<ErrorDTO> errors;

    @Data
    @AllArgsConstructor
    public static class ErrorDTO {
        private final String key;
        private final String value;
    }
}

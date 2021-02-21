package ru.vk.sladkiipirojok.questionnairerest.service.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public final class PageableDTO {
    @ApiParam(name = "Поле для сортировки",
            allowableValues = "name, beginDate, endDate")
    @Pattern(regexp = "name|beginDate|endDate",
            message = "Sort field Error")
    @NotBlank(message = "Sorting not set")
    private String sort;

    @ApiParam(name = "Порядок сортировки",
            defaultValue = "ASC",
            allowableValues = "ASC, DESC")
    @Pattern(regexp = "DESC|ASC",
            message = "Sort type error")
    private String order = "ASC";

    @ApiParam(value = "Номер страницы",
            defaultValue = "0")
    @PositiveOrZero(message = "page < 0")
    private int page = 0;
    @ApiParam(name = "Размер страницы",
            defaultValue = "20")
    @Positive(message = "size <=0")
    private int size = 20;
}

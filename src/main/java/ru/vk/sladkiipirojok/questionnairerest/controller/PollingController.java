package ru.vk.sladkiipirojok.questionnairerest.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vk.sladkiipirojok.questionnairerest.repository.model.Poll;
import ru.vk.sladkiipirojok.questionnairerest.service.PollService;
import ru.vk.sladkiipirojok.questionnairerest.service.dto.*;
import ru.vk.sladkiipirojok.questionnairerest.specification.PollSpecification;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/polls")
@Api("Контроллер для управления опроссами")
public final class PollingController {
    private final static String nameField = "name";
    private final static String beginDateField = "beginDate";
    private final static String endDateField = "endDate";
    private final static String activeField = "active";
    private final PollService pollService;

    @Autowired
    public PollingController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping
    @ApiOperation(value = "Получение страницы с опросами", response = PageDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = PageDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorsDTO.class)
    })
    public ResponseEntity<PageDTO> getPolling(@ApiParam(name = "Фильтрация данных по имени") @RequestParam(required = false) String name,
                                              @ApiParam(name = "Фильтрация данных по времени начала опроса", format = "yyyy-MM-dd", example = "2021-02-21") @RequestParam(required = false) LocalDate beginDate,
                                              @ApiParam(name = "Фильтрация данныпо по времени конца опроса", format = "yyyy-MM-dd", example = "2021-02-21") LocalDate endDate,
                                              @ApiParam(name = "Фильтрация данныпо по активности") @RequestParam(required = false) Boolean active,
                                              @Valid PageableDTO pageable) {
        Specification<Poll> filter = PollSpecification.name(nameField, name)
                .and(PollSpecification.date(beginDateField, beginDate))
                .and(PollSpecification.date(endDateField, endDate))
                .and(PollSpecification.active(activeField, active));
        return new ResponseEntity<>(pollService.getPolls(filter, pageable), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Создание нового опроса")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = PostDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorsDTO.class)
    })
    public ResponseEntity<PostDTO> addPoll(@Valid @RequestBody PollDTO poll) {
        long pollId = pollService.createPoll(poll);
        PostDTO response = new PostDTO(pollId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ApiOperation("Обновление существующего запроса или создание нового с заданным id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorsDTO.class)
    })
    public ResponseEntity<Void> updatePoll(@Valid @RequestBody PollDTO poll, @ApiParam(name = "id опроса") @PathVariable long id) {
        return new ResponseEntity<>(pollService.updatePoll(id, poll) ? HttpStatus.NO_CONTENT : HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Удаление опроса по id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    public ResponseEntity<Void> deletePoll(@ApiParam(name = "id опроса") @PathVariable long id) {
        return new ResponseEntity<>(pollService.deletePoll(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ErrorsDTO handleValidationExceptions(BindException ex) {
        List<ErrorsDTO.ErrorDTO> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(er -> new ErrorsDTO.ErrorDTO(((FieldError) er).getField(), er.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorsDTO(errors);
    }
}

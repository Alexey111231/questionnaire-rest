package ru.vk.sladkiipirojok.questionnairerest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.vk.sladkiipirojok.questionnairerest.repository.model.Poll;
import ru.vk.sladkiipirojok.questionnairerest.service.PollService;
import ru.vk.sladkiipirojok.questionnairerest.service.dto.PollDTO;
import ru.vk.sladkiipirojok.questionnairerest.specification.PollSpecification;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/polls")
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
    public ResponseEntity<Page<PollDTO>> getPolling(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) LocalDate beginDate,
                                                    @RequestParam(required = false) LocalDate endDate,
                                                    @RequestParam(required = false) Boolean active,
                                                    Pageable pageable) {
        assertPageable(pageable);
        Specification<Poll> filter = PollSpecification.name(nameField, name)
                .and(PollSpecification.date(beginDateField, beginDate))
                .and(PollSpecification.date(endDateField, endDate))
                .and(PollSpecification.active(activeField, active));
        return new ResponseEntity<>(pollService.getPolls(filter, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addPoll(@Valid @RequestBody PollDTO poll) {
        long pollId = pollService.createPoll(poll);

        LinkedHashMap<String, String> responseBody = new LinkedHashMap<>();
        responseBody.put("id", String.valueOf(pollId));

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updatePoll(@Valid @RequestBody PollDTO poll, @PathVariable long id) {
        return new ResponseEntity<>(pollService.updatePoll(id, poll) ? HttpStatus.NO_CONTENT : HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePoll(@PathVariable long id) {
        return new ResponseEntity<>(pollService.deletePoll(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    private static void assertPageable(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            throw new IllegalArgumentException("Unsorted");
        }
        boolean isSortCorrect = pageable.getSort()
                .stream()
                .allMatch(order -> order.getProperty().equals(nameField) ||
                        order.getProperty().equals(beginDateField) ||
                        order.getProperty().equals(endDateField));
        if (!isSortCorrect) {
            throw new IllegalArgumentException("Uncorrected sort field");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors.toMap(er -> ((FieldError) er).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    protected Map<String, String> handleValidateExceptions(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("sort", ex.getMessage());
        return errors;
    }
}

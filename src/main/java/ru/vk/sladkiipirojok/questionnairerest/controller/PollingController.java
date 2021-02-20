package ru.vk.sladkiipirojok.questionnairerest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.sladkiipirojok.questionnairerest.repository.model.Poll;
import ru.vk.sladkiipirojok.questionnairerest.service.PollService;
import ru.vk.sladkiipirojok.questionnairerest.specification.PollSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/polls")
public final class PollingController {

    private final PollService pollService;

    @Autowired
    public PollingController(PollService pollService) {
        this.pollService = pollService;
    }

    @GetMapping
    public ResponseEntity<Page<Poll>> getPolling(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) LocalDate beginDate,
                                                 @RequestParam(required = false) LocalDate endDate,
                                                 @RequestParam(required = false) Boolean active,
                                                 Pageable pageable) {
        System.out.println(name);
        System.out.println(beginDate);
        System.out.println(endDate);
        System.out.println(active);
        System.out.println(pageable);
        Specification<Poll> filter = PollSpecification.name("name", name)
                .and(PollSpecification.date("beginDate", beginDate))
                .and(PollSpecification.date("endDate", endDate))
                .and(PollSpecification.active("active", active));
        return new ResponseEntity<>(pollService.getPolls(filter, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addPoll(@RequestBody Poll poll) {
        LinkedHashMap<String, String> responseBody = new LinkedHashMap<>();
        responseBody.put("id", String.valueOf(pollService.createPoll(poll)));
        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updatePoll(@RequestBody Poll poll) {
        return new ResponseEntity<>(pollService.updatePoll(poll) ? HttpStatus.NO_CONTENT : HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePoll(@PathVariable long id) {
        return new ResponseEntity<>(pollService.deletePoll(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }
}

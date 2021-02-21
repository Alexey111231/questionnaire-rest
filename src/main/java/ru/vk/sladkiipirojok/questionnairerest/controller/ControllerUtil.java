package ru.vk.sladkiipirojok.questionnairerest.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.vk.sladkiipirojok.questionnairerest.controller.dto.PageableDTO;
import ru.vk.sladkiipirojok.questionnairerest.service.model.Poll;

public class ControllerUtil {
    private ControllerUtil() {
    }

    public static Pageable convertToPageable(PageableDTO pageableDTO) {
        return PageRequest.of(pageableDTO.getPage(),
                pageableDTO.getSize(),
                Sort.Direction.valueOf(pageableDTO.getOrder()),
                pageableDTO.getSort());
    }

    public static void mappingQuestion(Poll poll) {
        poll.getQuestions().forEach(question -> question.setPoll(poll));
    }
}

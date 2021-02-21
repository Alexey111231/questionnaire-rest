package ru.vk.sladkiipirojok.questionnairerest.service;

import org.springframework.data.jpa.domain.Specification;
import ru.vk.sladkiipirojok.questionnairerest.repository.model.Poll;
import ru.vk.sladkiipirojok.questionnairerest.service.dto.PageDTO;
import ru.vk.sladkiipirojok.questionnairerest.service.dto.PageableDTO;
import ru.vk.sladkiipirojok.questionnairerest.service.dto.PollDTO;

public interface PollService {
    PageDTO getPolls(Specification<Poll> specification, PageableDTO pageable);

    long createPoll(PollDTO pollDTO);

    boolean updatePoll(long id, PollDTO pollDTO);

    boolean deletePoll(long id);
}

package ru.vk.sladkiipirojok.questionnairerest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.vk.sladkiipirojok.questionnairerest.service.model.Poll;

public interface PollService {
    Page<Poll> getPolls(Specification<Poll> specification, Pageable pageable);

    long createPoll(Poll poll);

    boolean updatePoll(long id, Poll poll);

    boolean deletePoll(long id);
}

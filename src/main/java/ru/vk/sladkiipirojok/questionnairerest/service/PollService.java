package ru.vk.sladkiipirojok.questionnairerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.sladkiipirojok.questionnairerest.repository.PollRepository;
import ru.vk.sladkiipirojok.questionnairerest.repository.model.Poll;

@Service
public class PollService {
    PollRepository pollRepository;

    @Autowired
    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Page<Poll> getPolls(Specification<Poll> specification, Pageable pageable) {
        return pollRepository.findAll(specification, pageable);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public long createPoll(Poll poll) {
        mappingQuestion(poll);
        return pollRepository.save(poll).getId();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean updatePoll(Poll poll) {
        boolean isUpdate = false;
        if (pollRepository.findById(poll.getId()).isPresent()) {
            pollRepository.deleteById(poll.getId());
            isUpdate = true;
        }
        mappingQuestion(poll);
        pollRepository.save(poll);
        return isUpdate;
    }

    public boolean deletePoll(long id) {
        try {
            deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, noRollbackFor = EmptyResultDataAccessException.class)
    public void deleteById(long id) {
        pollRepository.deleteById(id);
    }

    private static void mappingQuestion(Poll poll) {
        poll.getQuestions().forEach(question -> question.setPoll(poll));
    }
}

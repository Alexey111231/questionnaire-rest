package ru.vk.sladkiipirojok.questionnairerest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.sladkiipirojok.questionnairerest.service.model.Poll;
import ru.vk.sladkiipirojok.questionnairerest.service.repository.PollRepository;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Page<Poll> getPolls(Specification<Poll> specification, Pageable pageable) {
        return pollRepository.findAll(specification, pageable);
    }

    @Transactional
    public long createPoll(Poll poll) {
        return pollRepository.save(poll).getId();
    }

    @Transactional
    public boolean updatePoll(long id, Poll poll) {
        poll.setId(id);
        boolean isUpdate = false;
        if (pollRepository.findById(poll.getId()).isPresent()) {
            pollRepository.deleteById(poll.getId());
            isUpdate = true;
        }
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

    @Transactional(noRollbackFor = EmptyResultDataAccessException.class)
    protected void deleteById(long id) {
        pollRepository.deleteById(id);
    }
}

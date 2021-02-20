package ru.vk.sladkiipirojok.questionnairerest.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.sladkiipirojok.questionnairerest.repository.PollRepository;
import ru.vk.sladkiipirojok.questionnairerest.repository.model.Poll;
import ru.vk.sladkiipirojok.questionnairerest.service.dto.PollDTO;

@Service
public class PollService {
    private final PollRepository pollRepository;

    private final ModelMapper mapper;

    @Autowired
    public PollService(PollRepository pollRepository, ModelMapper mapper) {
        this.pollRepository = pollRepository;
        this.mapper = mapper;
    }

    public Page<PollDTO> getPolls(Specification<Poll> specification, Pageable pageable) {
        return pollRepository.findAll(specification, pageable).map(poll -> mapper.map(poll, PollDTO.class));
    }

    @Transactional
    public long createPoll(PollDTO pollDTO) {
        Poll poll = mapper.map(pollDTO, Poll.class);
        mappingQuestion(poll);
        return pollRepository.save(poll).getId();
    }

    @Transactional
    public boolean updatePoll(long id, PollDTO pollDTO) {
        Poll poll = mapper.map(pollDTO, Poll.class);
        poll.setId(id);
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

    @Transactional(noRollbackFor = EmptyResultDataAccessException.class)
    protected void deleteById(long id) {
        pollRepository.deleteById(id);
    }

    private static void mappingQuestion(Poll poll) {
        poll.getQuestions().forEach(question -> question.setPoll(poll));
    }
}

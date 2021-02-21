package ru.vk.sladkiipirojok.questionnairerest.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.vk.sladkiipirojok.questionnairerest.service.model.Poll;

public interface PollRepository extends JpaRepository<Poll, Long>, JpaSpecificationExecutor<Poll> {
}

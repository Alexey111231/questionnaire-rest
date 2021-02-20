package ru.vk.sladkiipirojok.questionnairerest.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.vk.sladkiipirojok.questionnairerest.repository.model.Poll;

import java.time.LocalDate;

public class PollSpecification {
    private PollSpecification() {
    }

    public static Specification<Poll> name(String poolFieldName, String name) {
        return (root, query, cb) -> (name == null || name.isEmpty()) ?
                cb.isTrue(cb.literal(true)) :
                cb.equal(root.get(poolFieldName), name);
    }

    public static Specification<Poll> date(String poolFieldName, LocalDate date) {
        return (root, query, cb) -> date == null ?
                cb.isTrue(cb.literal(true)) :
                cb.equal(root.get(poolFieldName), date);
    }

    public static Specification<Poll> active(String poolFieldName, Boolean active) {
        return (root, query, cb) -> active == null ?
                cb.isTrue(cb.literal(true)) :
                cb.equal(root.get(poolFieldName), active);
    }
}

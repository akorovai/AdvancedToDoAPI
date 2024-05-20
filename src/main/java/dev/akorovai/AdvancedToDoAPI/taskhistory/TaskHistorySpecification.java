package dev.akorovai.AdvancedToDoAPI.taskhistory;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskHistorySpecification {

    public static Specification<TaskHistory> hasTaskId(Long taskId) {
        return (root, query, criteriaBuilder) -> taskId == null ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("task").get("id"), taskId);
    }

    public static Specification<TaskHistory> timestampBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("timestamp"), from, to);
    }
}

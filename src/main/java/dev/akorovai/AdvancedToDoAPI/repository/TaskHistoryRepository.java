package dev.akorovai.AdvancedToDoAPI.repository;

import dev.akorovai.AdvancedToDoAPI.entity.TaskHistory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findByTaskIdAndTimestampBetween(Long taskId, LocalDateTime from, LocalDateTime to, Sort sort);

    List<TaskHistory> findByTaskId(Long taskId, Sort sort);

    List<TaskHistory> findByTimestampBetween(LocalDateTime from, LocalDateTime to, Sort sort);
}

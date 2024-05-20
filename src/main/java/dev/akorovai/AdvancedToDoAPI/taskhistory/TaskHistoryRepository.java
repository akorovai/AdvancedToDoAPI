package dev.akorovai.AdvancedToDoAPI.taskhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long>, JpaSpecificationExecutor<TaskHistory> {
}


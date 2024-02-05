package dev.akorovai.AdvancedToDoAPI.repositories;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistoryRepository, Long> {
}

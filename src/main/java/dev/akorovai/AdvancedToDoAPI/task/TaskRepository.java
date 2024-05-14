package dev.akorovai.AdvancedToDoAPI.task;

import dev.akorovai.AdvancedToDoAPI.entity.Status;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}

package dev.akorovai.AdvancedToDoAPI.repositories;

import dev.akorovai.AdvancedToDoAPI.entities.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

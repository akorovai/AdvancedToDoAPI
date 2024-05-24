package dev.akorovai.AdvancedToDoAPI.repository;

import dev.akorovai.AdvancedToDoAPI.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}

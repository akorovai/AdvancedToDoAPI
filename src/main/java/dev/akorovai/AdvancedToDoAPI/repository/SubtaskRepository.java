package dev.akorovai.AdvancedToDoAPI.repository;

import dev.akorovai.AdvancedToDoAPI.entity.Subtask;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
}

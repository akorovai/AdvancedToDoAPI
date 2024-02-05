package dev.akorovai.AdvancedToDoAPI.repositories;

import dev.akorovai.AdvancedToDoAPI.entities.Label;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    boolean existsByTitle(String title);
}

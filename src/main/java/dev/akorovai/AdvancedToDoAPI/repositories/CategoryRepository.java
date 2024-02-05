package dev.akorovai.AdvancedToDoAPI.repositories;

import dev.akorovai.AdvancedToDoAPI.entities.Category;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}

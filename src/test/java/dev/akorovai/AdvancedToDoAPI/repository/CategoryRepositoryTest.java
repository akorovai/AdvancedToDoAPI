package dev.akorovai.AdvancedToDoAPI.repository;

import dev.akorovai.AdvancedToDoAPI.category.Category;
import dev.akorovai.AdvancedToDoAPI.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void existsByName_ShouldReturnTrueIfExists() {
        // Given
        String categoryName = "Test Category";
        Category category = Category.builder().name(categoryName).build();
        entityManager.persistAndFlush(category);

        // When
        boolean exists = categoryRepository.existsByName(categoryName);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void existsByName_ShouldReturnFalseIfNotExists() {
        // Given
        String categoryName = "Nonexistent Category";

        // When
        boolean exists = categoryRepository.existsByName(categoryName);

        // Then
        assertThat(exists).isFalse();
    }
}

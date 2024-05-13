package dev.akorovai.AdvancedToDoAPI;

import dev.akorovai.AdvancedToDoAPI.category.Category;
import dev.akorovai.AdvancedToDoAPI.category.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() {
        List<Category> categories = createCategories();
        categoryRepository.saveAll(categories);
    }

    private List<Category> createCategories() {
        List<Category> categoryList = new ArrayList<>();

        Category category1 = new Category();
        category1.setName("Work");
        category1.setCreatedAt(LocalDateTime.now());
        category1.setModifiedAt(LocalDateTime.now());
        categoryList.add(category1);

        Category category2 = new Category();
        category2.setName("Personal");
        category2.setCreatedAt(LocalDateTime.now());
        category2.setModifiedAt(LocalDateTime.now());
        categoryList.add(category2);

        return categoryList;
    }




}

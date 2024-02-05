package dev.akorovai.AdvancedToDoAPI;

import dev.akorovai.AdvancedToDoAPI.entities.Category;
import dev.akorovai.AdvancedToDoAPI.entities.Label;
import dev.akorovai.AdvancedToDoAPI.repositories.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.repositories.LabelRepository;
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
    private final LabelRepository labelRepository;

    @Override
    public void run(String... args) throws Exception {
        seedCategories();
    }

    private void seedCategories() {
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

        categoryRepository.saveAll(categoryList);

        List<Label> labelList = new ArrayList<>();

        Label label1 = new Label();
        label1.setTitle("Urgent");
        label1.setCreatedAt(LocalDateTime.now());
        label1.setModifiedAt(LocalDateTime.now());
        labelList.add(label1);

        Label label2 = new Label();
        label2.setTitle("Important");
        label2.setCreatedAt(LocalDateTime.now());
        label2.setModifiedAt(LocalDateTime.now());
        labelList.add(label2);

        labelRepository.saveAll(labelList);
    }
}

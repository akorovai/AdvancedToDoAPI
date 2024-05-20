package dev.akorovai.AdvancedToDoAPI;

import dev.akorovai.AdvancedToDoAPI.entity.*;
import dev.akorovai.AdvancedToDoAPI.repository.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() {
        seedCategories();
        seedTasks();
    }

    private void seedCategories() {
        // Create sample categories
        Category category1 = new Category();
        category1.setName("Work");
        Category category2 = new Category();
        category2.setName("Computer Science");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    private void seedTasks() {
        // Create sample tasks
        Task task1 = new Task();
        task1.setTitle("Finish Project");
        task1.setDescription("Complete the project before the deadline.");
        task1.setCategory(categoryRepository.findById(1L).orElse(null));
        task1.setPriority('A');
        task1.setStatus(Status.CREATED);
        task1.setTaskType(TaskType.ORDERED);
        task1.setDueDate(LocalDateTime.now().plusDays(7));

        Set<Subtask> subtasksForTask1 = buildSubtasksForTask1();

        subtasksForTask1.forEach(subtask -> subtask.setTask(task1));
        task1.setSubtasks(subtasksForTask1);

        taskRepository.save(task1);


        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Gym Workout");
        task2.setDescription("Work out at the gym for an hour.");
        task2.setCategory(categoryRepository.findById(2L).orElse(null));
        task2.setPriority('B');
        task2.setStatus(Status.CREATED);
        task2.setTaskType(TaskType.SIMPLE);
        task2.setDueDate(LocalDateTime.now().plusDays(7));

        taskRepository.save(task2);
    }

    private Set<Subtask> buildSubtasksForTask1() {

        Subtask subtask1 = new Subtask("Task Analysis", Status.CREATED, 1);
        Subtask subtask2 = new Subtask("Code Implementation", Status.CREATED, 2);
        Subtask subtask3 = new Subtask("Testing", Status.CREATED, 3);


        Set<Subtask> subtasks = new HashSet<>();
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        subtasks.add(subtask3);

        return subtasks;

    }
}

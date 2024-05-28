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
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        seedCategories();
        seedTasks();
    }

    private void seedCategories() {
        Category category1 = new Category();
        category1.setName("Work");
        Category category2 = new Category();
        category2.setName("Computer Science");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    private void seedTasks() {
        Task task1 = new Task();
        task1.setTitle("Ordered Task");
        task1.setDescription("An ordered task with subtasks.");
        task1.setCategory(categoryRepository.findById(1L).orElse(null));
        task1.setPriority('A');
        task1.setStatus(Status.CREATED);
        task1.setTaskType(TaskType.ORDERED);
        task1.setDueDate(LocalDateTime.now().plusDays(7));

        Set<Subtask> subtasksForTask1 = buildSubtasksForOrderedTask();

        subtasksForTask1.forEach(subtask -> subtask.setTask(task1));
        task1.setSubtasks(subtasksForTask1);

        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Simple Task");
        task2.setDescription("A simple task without subtasks.");
        task2.setCategory(categoryRepository.findById(2L).orElse(null));
        task2.setPriority('B');
        task2.setStatus(Status.CREATED);
        task2.setTaskType(TaskType.SIMPLE);
        task2.setDueDate(LocalDateTime.now().plusDays(7));

        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Summary Task");
        task3.setDescription("A task with subtasks.");
        task3.setCategory(categoryRepository.findById(1L).orElse(null));
        task3.setPriority('C');
        task3.setStatus(Status.CREATED);
        task3.setTaskType(TaskType.SUMMARY);
        task3.setDueDate(LocalDateTime.now().plusDays(7));

        Set<Subtask> subtasksForTask3 = buildSubtasksForSummaryTask();

        subtasksForTask3.forEach(subtask -> subtask.setTask(task3));
        task3.setSubtasks(subtasksForTask3);

        taskRepository.save(task3);
    }

    private Set<Subtask> buildSubtasksForOrderedTask() {
        Set<Subtask> subtasks = new HashSet<>();

        Subtask subtask1 = new Subtask();
        subtask1.setTitle("Subtask 1");
        subtask1.setStatus(Status.CREATED);
        subtask1.setOrderIndex(1);
        subtasks.add(subtask1);

        Subtask subtask2 = new Subtask();
        subtask2.setTitle("Subtask 2");
        subtask2.setStatus(Status.CREATED);
        subtask2.setOrderIndex(2);
        subtasks.add(subtask2);

        Subtask subtask3 = new Subtask();
        subtask3.setTitle("Subtask 3");
        subtask3.setStatus(Status.CREATED);
        subtask3.setOrderIndex(3);
        subtasks.add(subtask3);

        return subtasks;
    }

    private Set<Subtask> buildSubtasksForSummaryTask() {
        Set<Subtask> subtasks = new HashSet<>();

        Subtask subtask1 = new Subtask();
        subtask1.setTitle("Subtask 1");
        subtask1.setStatus(Status.CREATED);
        subtasks.add(subtask1);

        Subtask subtask2 = new Subtask();
        subtask2.setTitle("Subtask 2");
        subtask2.setStatus(Status.CREATED);
        subtasks.add(subtask2);

        Subtask subtask3 = new Subtask();
        subtask3.setTitle("Subtask 3");
        subtask3.setStatus(Status.CREATED);
        subtasks.add(subtask3);

        return subtasks;
    }
}

package dev.akorovai.AdvancedToDoAPI.task;

import dev.akorovai.AdvancedToDoAPI.category.Category;
import dev.akorovai.AdvancedToDoAPI.category.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.category.categoryExceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.task.taskExceptionHandler.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;


    @Override
    public TaskDto getTaskById(Long IdTask) throws TaskNotFoundException {
        Task task = taskRepository.findById(IdTask).orElseThrow(() -> new TaskNotFoundException(IdTask));

        log.info("Retrieved task successfully: {}", task.getTitle());

        return modelMapper.map(task, TaskDto.class);
    }


    @Override
    public TaskDto addNewTask(NewTaskDto newTaskDto) {

        validateTaskType(newTaskDto);
        validatePriority(newTaskDto.getPriority());
        validateSubtasks(newTaskDto);

        Task newTask = modelMapper.map(newTaskDto, Task.class);
        newTask.setStatus(Status.CREATED);

        Category category = categoryRepository.findById(newTaskDto.getIdCategory()).orElseThrow(() -> new CategoryNotFoundException(newTaskDto.getIdCategory()));
        newTask.setCategory(category);


        Task savedTask = taskRepository.save(newTask);

        log.info("New task added successfully: {}", savedTask.getTitle());

        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public void deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        taskRepository.delete(task);

        log.info("Task deleted successfully with ID: {}", taskId);
    }

    @Override
    public TaskDto modifyTask(Long idTask, ModifiedTaskDto mtd) {
        Optional<Task> optionalTask = taskRepository.findById(idTask);
        if (optionalTask.isEmpty()) {
            throw new TaskNotFoundException(idTask);
        }
        Task task = optionalTask.get();

        String newTitle = mtd.getTitle();
        if (!isNotEmptyField(newTitle)) {
            task.setTitle(newTitle);
        }

        String newDescription = mtd.getDescription();
        if (!isNotEmptyField(newDescription)) {
            task.setDescription(newDescription);
        }

        Character newPriority = mtd.getPriority();
        if (newPriority != null) {
            validatePriority(newPriority);
            task.setPriority(newPriority);
        }

        LocalDateTime newDueDate = mtd.getDueDate();
        if (newDueDate != null && newDueDate.isAfter(LocalDateTime.now())) {
            task.setDueDate(newDueDate);
        }

        Long categoryId = mtd.getIdCategory();
        if (categoryId != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            optionalCategory.ifPresent(task::setCategory);
        }


        Task updatedTask = taskRepository.save(task);


        log.info("Task modified successfully: {}", updatedTask.getId());


        return modelMapper.map(updatedTask, TaskDto.class);
    }


    @Override
    public TaskDto moveToNextStep(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Status currentStatus = task.getStatus();
        Status newStatus = getNextStatus(currentStatus);
        task.setStatus(newStatus);
        taskRepository.save(task);

        log.info("Task {} moved to next step. New status: {}", taskId, newStatus);

        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public TaskDto moveToPreviousStep(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Status currentStatus = task.getStatus();
        Status newStatus = getPreviousStatus(currentStatus);
        task.setStatus(newStatus);
        taskRepository.save(task);

        log.info("Task {} moved to previous step. New status: {}", taskId, newStatus);

        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasksSorted(String sortBy) {
        List<Task> tasks = switch (sortBy) {
            case "status" -> taskRepository.findAll(Sort.by(Sort.Direction.ASC, "status"));
            case "dueDate" -> taskRepository.findAll(Sort.by(Sort.Direction.ASC, "dueDate"));
            default -> taskRepository.findAll();
        };
        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }


    private boolean isNotEmptyField(String field) {
        return field == null || field.trim().isEmpty();
    }

    private Status getNextStatus(Status currentStatus) {
        return switch (currentStatus) {
            case CREATED -> Status.IN_PROGRESS;
            case IN_PROGRESS -> Status.DONE;
            default -> throw new InappropriateProgressStatusException(currentStatus);
        };
    }

    private Status getPreviousStatus(Status currentStatus) {
        return switch (currentStatus) {
            case DONE -> Status.IN_PROGRESS;
            case IN_PROGRESS -> Status.CREATED;
            default -> throw new InappropriateProgressStatusException(currentStatus);
        };
    }

    private void validateTaskType(NewTaskDto newTaskDto) {
        Arrays.stream(TaskType.values()).filter(taskType -> taskType == newTaskDto.getTaskType()).findAny().orElseThrow(() -> new TaskTypeNotFoundException(newTaskDto.getTaskType()));
    }

    private void validatePriority(Character priority) {
        if (!Character.isLetter(priority)) {
            throw new IncorrectPriorityException(priority);
        }
    }

    private void validateSubtasks(NewTaskDto newTaskDto) {
        if (newTaskDto.getTaskType() == TaskType.SIMPLE && !newTaskDto.getSubtasks().isEmpty()) {
            throw new SimpleTaskWithSubtasksException();
        }

        if (newTaskDto.getTaskType() != TaskType.SIMPLE && (newTaskDto.getSubtasks() == null || newTaskDto.getSubtasks().size() < 2)) {
            throw new NotSimpleTaskWithInsufficientSubtasksException(newTaskDto.getSubtasks() != null ? newTaskDto.getSubtasks().size() : 0);
        }
    }
}

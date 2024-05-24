package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.*;
import dev.akorovai.AdvancedToDoAPI.entity.*;
import dev.akorovai.AdvancedToDoAPI.exception.categoryExceptions.CategoryNotFoundException;
import dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler.TaskNotFoundException;
import dev.akorovai.AdvancedToDoAPI.repository.CategoryRepository;
import dev.akorovai.AdvancedToDoAPI.repository.SubtaskRepository;
import dev.akorovai.AdvancedToDoAPI.repository.TaskHistoryRepository;
import dev.akorovai.AdvancedToDoAPI.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private SubtaskRepository subtaskRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TaskHistoryRepository taskHistoryRepository;

    @InjectMocks
    private TaskServiceImpl underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getTaskById_shouldReturnTaskDto_whenTaskExists() throws TaskNotFoundException {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Sample Task");
        task.setSubtasks(new HashSet<>());
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        TaskDto taskDto = new TaskDto();
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        // When
        TaskDto result = underTest.getTaskById(taskId);

        // Then
        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTaskById_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(TaskNotFoundException.class, () -> underTest.getTaskById(taskId));
    }

    @Test
    void addNewTask_shouldReturnTaskDto_whenValidNewTaskDtoProvided() {
        // Given
        NewTaskDto newTaskDto = new NewTaskDto();
        newTaskDto.setTitle("New Task");
        newTaskDto.setPriority('A');
        newTaskDto.setTaskType(TaskType.ORDERED);
        newTaskDto.setIdCategory(1L);

        // Adding required subtasks
        SubtaskDto subtask1 = new SubtaskDto();
        subtask1.setTitle("Subtask 1");
        SubtaskDto subtask2 = new SubtaskDto();
        subtask2.setTitle("Subtask 2");
        newTaskDto.setSubtasks(Set.of(subtask1, subtask2));

        Task task = new Task();
        task.setId(1L);
        Category category = new Category();
        category.setId(1L);
        category.setName("Category Name");

        when(categoryRepository.findById(newTaskDto.getIdCategory())).thenReturn(Optional.of(category));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDto taskDto = new TaskDto();
        when(modelMapper.map(any(Task.class), eq(TaskDto.class))).thenReturn(taskDto);

        // When
        TaskDto result = underTest.addNewTask(newTaskDto);


        // Then
        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    void addNewTask_shouldThrowCategoryNotFoundException_whenCategoryDoesNotExist() {
        // Given
        NewTaskDto newTaskDto = new NewTaskDto();
        newTaskDto.setTitle("Sample Task");
        newTaskDto.setDescription("This is a sample task description.");
        newTaskDto.setPriority('A');
        newTaskDto.setDueDate(LocalDateTime.now());
        newTaskDto.setIdCategory(1L);
        newTaskDto.setTaskType(TaskType.SIMPLE);
        newTaskDto.setSubtasks(new HashSet<>());


        when(categoryRepository.findById(newTaskDto.getIdCategory())).thenReturn(Optional.empty());

        // When, Then
        assertThrows(CategoryNotFoundException.class, () -> underTest.addNewTask(newTaskDto));
    }

    @Test
    void deleteTaskById_shouldDeleteTask_whenTaskExists() {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // When
        underTest.deleteTaskById(taskId);

        // Then
        verify(taskRepository, times(1)).delete(task);
        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    void deleteTaskById_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(TaskNotFoundException.class, () -> underTest.deleteTaskById(taskId));
    }

    @Test
    void modifyTask_shouldReturnModifiedTaskDto_whenTaskExists() {
        // Given
        Long taskId = 1L;
        ModifiedTaskDto modifiedTaskDto = new ModifiedTaskDto();
        modifiedTaskDto.setTitle("Modified Task");
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Task modifiedTask = new Task();
        when(taskRepository.save(task)).thenReturn(modifiedTask);
        TaskDto taskDto = new TaskDto();
        when(modelMapper.map(modifiedTask, TaskDto.class)).thenReturn(taskDto);

        // When
        TaskDto result = underTest.modifyTask(taskId, modifiedTaskDto);

        // Then
        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).save(task);
        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    void modifyTask_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        // Given
        Long taskId = 1L;
        ModifiedTaskDto modifiedTaskDto = new ModifiedTaskDto();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(TaskNotFoundException.class, () -> underTest.modifyTask(taskId, modifiedTaskDto));
    }

    @Test
    void moveToNextStep_shouldUpdateTaskStatus_whenTaskExists() {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setStatus(Status.CREATED);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task updatedTask = new Task();

        updatedTask.setStatus(Status.IN_PROGRESS);
        when(taskRepository.save(task)).thenReturn(updatedTask);
        TaskDto taskDto = new TaskDto();
        taskDto.setStatus(Status.IN_PROGRESS); // Ensure this line matches your DTO implementation
        when(modelMapper.map(updatedTask, TaskDto.class)).thenReturn(taskDto);

        // When
        TaskDto result = underTest.moveToNextStep(taskId);

        // Then
        assertNotNull(result);
        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).save(task);
        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
        verify(modelMapper, times(1)).map(updatedTask, TaskDto.class);
    }


    @Test
    void moveToNextStep_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(TaskNotFoundException.class, () -> underTest.moveToNextStep(taskId));
    }

    @Test
    void moveToPreviousStep_shouldUpdateTaskStatus_whenTaskExists() {
        // Given
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setStatus(Status.DONE);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task updatedTask = new Task();
        updatedTask.setStatus(Status.IN_PROGRESS);
        when(taskRepository.save(task)).thenReturn(updatedTask);
        TaskDto taskDto = new TaskDto();
        when(modelMapper.map(updatedTask, TaskDto.class)).thenReturn(taskDto);

        // When
        TaskDto result = underTest.moveToPreviousStep(taskId);

        // Then
        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).save(task); // Verify the save method call with the correct task object
        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }


    @Test
    void moveToPreviousStep_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(TaskNotFoundException.class, () -> underTest.moveToPreviousStep(taskId));
    }

    @Test
    void getAllTasks_shouldReturnListOfTaskDtos() {
        // Given
        Task task = Task.builder()
                .id(1L)
                .title("Sample Task")
                .description("This is a sample task description.")
                .priority('A')
                .category(new Category())
                .dueDate(LocalDateTime.now().plusDays(3))
                .status(Status.CREATED)
                .taskType(TaskType.SIMPLE)
                .subtasks(new HashSet<>())
                .histories(new HashSet<>())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        List<Task> tasks = Collections.singletonList(task);

        when(taskRepository.findAll()).thenReturn(tasks);

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setPriority(task.getPriority());
        taskDto.setCategory(new CategoryDto());
        taskDto.setDueDate(task.getDueDate());
        taskDto.setStatus(task.getStatus());
        taskDto.setTaskType(task.getTaskType());
        taskDto.setSubtasks(new ArrayList<>());

        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        // When
        List<TaskDto> result = underTest.getAllTasksSorted("");

        // Then
        assertNotNull(result);
        assertThat(result, hasSize(1));
        assertThat(result.get(0), samePropertyValuesAs(taskDto));
        verify(taskRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(task, TaskDto.class);

        // Additional Tests for sorting
        List<TaskDto> resultSortedByStatus = underTest.getAllTasksSorted("status");
        assertNotNull(resultSortedByStatus);
        assertThat(resultSortedByStatus, hasSize(1));
        assertThat(resultSortedByStatus.get(0), samePropertyValuesAs(taskDto));

        List<TaskDto> resultSortedByDueDate = underTest.getAllTasksSorted("dueDate");
        assertNotNull(resultSortedByDueDate);
        assertThat(resultSortedByDueDate, hasSize(1));
        assertThat(resultSortedByDueDate.get(0), samePropertyValuesAs(taskDto));
    }


}

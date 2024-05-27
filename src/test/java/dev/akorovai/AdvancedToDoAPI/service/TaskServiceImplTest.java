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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Sample Task");
        task.setSubtasks(new HashSet<>());
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        TaskDto taskDto = new TaskDto();
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        TaskDto result = underTest.getTaskById(taskId);

        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTaskById_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> underTest.getTaskById(taskId));
    }

    @Test
    void addNewTask_shouldReturnTaskDto_whenValidNewTaskDtoProvided() {
        NewTaskDto newTaskDto = new NewTaskDto();
        newTaskDto.setTitle("New Task");
        newTaskDto.setPriority('A');
        newTaskDto.setTaskType(TaskType.ORDERED);
        newTaskDto.setIdCategory(1L);

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

        TaskDto result = underTest.addNewTask(newTaskDto);

        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    void addNewTask_shouldThrowCategoryNotFoundException_whenCategoryDoesNotExist() {
        NewTaskDto newTaskDto = new NewTaskDto();
        newTaskDto.setTitle("Sample Task");
        newTaskDto.setDescription("This is a sample task description.");
        newTaskDto.setPriority('A');
        newTaskDto.setDueDate(LocalDateTime.now());
        newTaskDto.setIdCategory(1L);
        newTaskDto.setTaskType(TaskType.SIMPLE);
        newTaskDto.setSubtasks(new HashSet<>());

        when(categoryRepository.findById(newTaskDto.getIdCategory())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> underTest.addNewTask(newTaskDto));
    }

    @Test
    void deleteTaskById_shouldDeleteTask_whenTaskExists() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        underTest.deleteTaskById(taskId);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void deleteTaskById_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> underTest.deleteTaskById(taskId));
    }

    @Test
    void modifyTask_shouldReturnModifiedTaskDto_whenTaskExists() {
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

        TaskDto result = underTest.modifyTask(taskId, modifiedTaskDto);

        assertEquals(taskDto, result);
        verify(taskRepository, times(1)).save(task);
        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
    }

    @Test
    void modifyTask_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        Long taskId = 1L;
        ModifiedTaskDto modifiedTaskDto = new ModifiedTaskDto();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> underTest.modifyTask(taskId, modifiedTaskDto));
    }

    @Test
    void getAllTasks_shouldReturnListOfTaskDtos() {
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

        List<TaskDto> result = underTest.getAllTasksSorted("");

        assertNotNull(result);
        assertThat(result, hasSize(1));
        assertThat(result.get(0), samePropertyValuesAs(taskDto));
        verify(taskRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(task, TaskDto.class);

        List<TaskDto> resultSortedByStatus = underTest.getAllTasksSorted("status");
        assertNotNull(resultSortedByStatus);
        assertThat(resultSortedByStatus, hasSize(1));
        assertThat(resultSortedByStatus.get(0), samePropertyValuesAs(taskDto));

        List<TaskDto> resultSortedByDueDate = underTest.getAllTasksSorted("dueDate");
        assertNotNull(resultSortedByDueDate);
        assertThat(resultSortedByDueDate, hasSize(1));
        assertThat(resultSortedByDueDate.get(0), samePropertyValuesAs(taskDto));
    }

    @Test
    void moveToNextStep_shouldUpdateStatusOfTaskOrSubtask_whenTaskExists() {
        Long taskId = 1L;
        Long subtaskId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(Status.IN_PROGRESS);

        Subtask subtask = new Subtask();
        subtask.setId(subtaskId);
        subtask.setStatus(Status.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(subtaskRepository.findById(subtaskId)).thenReturn(Optional.of(subtask));
        when(taskRepository.save(task)).thenReturn(task);
        when(subtaskRepository.save(subtask)).thenReturn(subtask);

        TaskDto taskDto = new TaskDto();
        SubtaskDto subtaskDto = new SubtaskDto();

        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);
        when(modelMapper.map(subtask, SubtaskDto.class)).thenReturn(subtaskDto);

        TaskDto resultTask = underTest.moveToNextStep(taskId, null);


        assertEquals(taskDto, resultTask);


        verify(taskRepository, times(1)).save(task);

    }

    @Test
    void moveToNextStep_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> underTest.moveToNextStep(taskId, null));
    }

    @Test
    void moveToPreviousStep_shouldUpdateStatusOfTaskOrSubtask_whenTaskExists() {
        Long taskId = 1L;
        Long subtaskId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setStatus(Status.IN_PROGRESS);

        Subtask subtask = new Subtask();
        subtask.setId(subtaskId);
        subtask.setStatus(Status.IN_PROGRESS);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(subtaskRepository.findById(subtaskId)).thenReturn(Optional.of(subtask));
        when(taskRepository.save(task)).thenReturn(task);
        when(subtaskRepository.save(subtask)).thenReturn(subtask);

        TaskDto taskDto = new TaskDto();
        SubtaskDto subtaskDto = new SubtaskDto();

        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);
        when(modelMapper.map(subtask, SubtaskDto.class)).thenReturn(subtaskDto);

        TaskDto resultTask = underTest.moveToPreviousStep(taskId, null);


        assertEquals(taskDto, resultTask);


        verify(taskRepository, times(1)).save(task);

    }

    @Test
    void moveToPreviousStep_shouldThrowTaskNotFoundException_whenTaskDoesNotExist() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> underTest.moveToPreviousStep(taskId, null));
    }
}

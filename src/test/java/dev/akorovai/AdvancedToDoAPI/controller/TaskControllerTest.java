package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.dto.ModifiedTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.NewTaskDto;
import dev.akorovai.AdvancedToDoAPI.dto.TaskDto;
import dev.akorovai.AdvancedToDoAPI.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void testGetTaskById() {
        // Arrange
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Test Task");
        when(taskService.getTaskById(anyLong())).thenReturn(taskDto);

        // Act
        ResponseEntity<TaskDto> response = taskController.getTaskById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Task", response.getBody().getTitle());
    }

    @Test
    void testGetAllTasksSorted() {
        // Arrange
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Sorted Task");
        when(taskService.getAllTasksSorted("status")).thenReturn(Collections.singletonList(taskDto));

        // Act
        ResponseEntity<List<TaskDto>> response = taskController.getAllTasksSorted("status");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Sorted Task", response.getBody().get(0).getTitle());
    }

    @Test
    void testAddNewTask() {
        // Arrange
        NewTaskDto newTaskDto = new NewTaskDto();
        newTaskDto.setTitle("New Task");
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("New Task");
        when(taskService.addNewTask(any(NewTaskDto.class))).thenReturn(taskDto);

        // Act
        ResponseEntity<TaskDto> response = taskController.addNewTask(newTaskDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New Task", response.getBody().getTitle());
    }

    @Test
    void testDeleteTask() {
        // Arrange
        doNothing().when(taskService).deleteTaskById(anyLong());

        // Act
        ResponseEntity<?> response = taskController.deleteTask(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testModifyTask() {
        // Arrange
        ModifiedTaskDto modifiedTaskDto = new ModifiedTaskDto();
        modifiedTaskDto.setTitle("Modified Task");
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Modified Task");
        when(taskService.modifyTask(anyLong(), any(ModifiedTaskDto.class))).thenReturn(taskDto);

        // Act
        ResponseEntity<TaskDto> response = taskController.modifyTask(modifiedTaskDto, 1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Modified Task", response.getBody().getTitle());
    }

    @Test
    void testChangeStatusToNextStep() {
        // Arrange
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Next Step Task");
        when(taskService.moveToNextStep(anyLong(), anyLong())).thenReturn(taskDto);

        // Act
        ResponseEntity<TaskDto> response = taskController.changeStatusToNextStep(1L, null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Next Step Task", response.getBody().getTitle());
    }

    @Test
    void testChangeStatusToPreviousStep() {
        // Arrange
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Previous Step Task");
        when(taskService.moveToPreviousStep(anyLong(), anyLong())).thenReturn(taskDto);

        // Act
        ResponseEntity<TaskDto> response = taskController.changeStatusToPreviousStep(1L, null);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Previous Step Task", response.getBody().getTitle());
    }
}

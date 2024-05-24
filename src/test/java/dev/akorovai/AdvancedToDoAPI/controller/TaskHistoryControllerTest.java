package dev.akorovai.AdvancedToDoAPI.controller;

import dev.akorovai.AdvancedToDoAPI.dto.TaskHistoryDto;
import dev.akorovai.AdvancedToDoAPI.service.TaskHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskHistoryControllerTest {

    @Mock
    private TaskHistoryService taskHistoryService;

    @InjectMocks
    private TaskHistoryController taskHistoryController;

    @Test
    void getTaskHistories_ReturnsOKResponse_WithTaskHistories() {
        // Arrange
        Long taskId = 1L;
        LocalDateTime from = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2022, 12, 31, 23, 59);
        String sortBy = "timestamp";

        TaskHistoryDto taskHistoryDto = new TaskHistoryDto();
        taskHistoryDto.setId(1L);
        taskHistoryDto.setIdTask(taskId);
        List<TaskHistoryDto> expected = new ArrayList<>();
        expected.add(taskHistoryDto);

        when(taskHistoryService.getTaskHistories(taskId, from, to, sortBy)).thenReturn(expected);

        // Act
        ResponseEntity<?> responseEntity = taskHistoryController.getTaskHistories(taskId, from, to, sortBy);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());

        verify(taskHistoryService, times(1)).getTaskHistories(taskId, from, to, sortBy);
    }

    @Test
    void getTaskHistories_ReturnsOKResponse_WithNoTaskHistories() {
        // Arrange
        Long taskId = 1L;
        LocalDateTime from = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2022, 12, 31, 23, 59);
        String sortBy = "timestamp";

        List<TaskHistoryDto> expected = new ArrayList<>();

        when(taskHistoryService.getTaskHistories(taskId, from, to, sortBy)).thenReturn(expected);

        // Act
        ResponseEntity<?> responseEntity = taskHistoryController.getTaskHistories(taskId, from, to, sortBy);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expected, responseEntity.getBody());

        verify(taskHistoryService, times(1)).getTaskHistories(taskId, from, to, sortBy);
    }
}

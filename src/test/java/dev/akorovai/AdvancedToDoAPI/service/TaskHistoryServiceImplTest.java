package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.TaskHistoryDto;
import dev.akorovai.AdvancedToDoAPI.entity.Task;
import dev.akorovai.AdvancedToDoAPI.entity.TaskHistory;
import dev.akorovai.AdvancedToDoAPI.repository.TaskHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskHistoryServiceImplTest {
    @Mock
    private TaskHistoryRepository taskHistoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskHistoryServiceImpl taskHistoryService;

    @Test
    void getTaskHistories() {
        // Arrange
        Long taskId = 1L;
        LocalDateTime from = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2022, 12, 31, 23, 59);
        String sortBy = "timestamp";

        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(1L);
        Task task = new Task();
        task.setId(taskId);
        taskHistory.setTask(task);
        List<TaskHistory> taskHistoryList = new ArrayList<>();
        taskHistoryList.add(taskHistory);

        when(taskHistoryRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(taskHistoryList);

        TaskHistoryDto taskHistoryDto = new TaskHistoryDto();
        taskHistoryDto.setId(1L);
        taskHistoryDto.setIdTask(taskId);
        when(modelMapper.map(any(TaskHistory.class), eq(TaskHistoryDto.class))).thenReturn(taskHistoryDto);

        // Act
        List<TaskHistoryDto> result = taskHistoryService.getTaskHistories(taskId, from, to, sortBy);

        // Assert
        assertEquals(1, result.size());
        TaskHistoryDto resultDto = result.get(0);
        assertEquals(1L, resultDto.getId());
        assertEquals(taskId, resultDto.getIdTask());

        verify(taskHistoryRepository, times(1)).findAll(any(Specification.class), any(Sort.class));
        verify(modelMapper, times(1)).map(any(TaskHistory.class), eq(TaskHistoryDto.class));
    }
}

package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.TaskHistoryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskHistoryService {
    List<TaskHistoryDto> getTaskHistories(Long taskId, LocalDateTime from, LocalDateTime to, String sortBy);
}

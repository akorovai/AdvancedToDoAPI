package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.TaskHistoryDto;
import dev.akorovai.AdvancedToDoAPI.entity.TaskHistory;
import dev.akorovai.AdvancedToDoAPI.repository.TaskHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final ModelMapper modelMapper;
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public List<TaskHistoryDto> getTaskHistories(Long taskId, LocalDateTime from, LocalDateTime to, String sortBy) {
        log.debug("Fetching task histories with taskId: {}, from: {}, to: {}, sortBy: {}", taskId, from, to, sortBy);

        List<TaskHistory> taskHistories = getTaskHistoriesFromRepository(taskId, from, to, sortBy);

        log.debug("Found {} task histories", taskHistories.size());

        return taskHistories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private List<TaskHistory> getTaskHistoriesFromRepository(Long taskId, LocalDateTime from, LocalDateTime to, String sortBy) {
        if (taskId != null && from != null && to != null) {
            return taskHistoryRepository.findByTaskIdAndTimestampBetween(taskId, from, to, Sort.by(Sort.Direction.ASC, sortBy));
        } else if (taskId != null) {
            return taskHistoryRepository.findByTaskId(taskId, Sort.by(Sort.Direction.ASC, sortBy));
        } else if (from != null && to != null) {
            return taskHistoryRepository.findByTimestampBetween(from, to, Sort.by(Sort.Direction.ASC, sortBy));
        } else {
            return taskHistoryRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
        }
    }

    private TaskHistoryDto convertToDto(TaskHistory taskHistory) {
        TaskHistoryDto taskHistoryDto = modelMapper.map(taskHistory, TaskHistoryDto.class);
        taskHistoryDto.setIdTask(taskHistory.getTask().getId());
        return taskHistoryDto;
    }
}

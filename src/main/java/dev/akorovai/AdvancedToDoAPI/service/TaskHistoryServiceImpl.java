package dev.akorovai.AdvancedToDoAPI.service;

import dev.akorovai.AdvancedToDoAPI.dto.TaskHistoryDto;
import dev.akorovai.AdvancedToDoAPI.entity.TaskHistory;
import dev.akorovai.AdvancedToDoAPI.repository.TaskHistoryRepository;
import dev.akorovai.AdvancedToDoAPI.repository.TaskHistorySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final ModelMapper modelMapper;
    private final TaskHistoryRepository taskHistoryRepository;

    @Override
    public List<TaskHistoryDto> getTaskHistories(Long taskId, LocalDateTime from, LocalDateTime to, String sortBy) {
        if (from == null) from = LocalDateTime.MIN;
        if (to == null) to = LocalDateTime.MAX;

        Specification<TaskHistory> specification = Specification
                .where(TaskHistorySpecification.hasTaskId(taskId))
                .and(TaskHistorySpecification.timestampBetween(from, to));

        List<TaskHistory> taskHistories = taskHistoryRepository.findAll(specification, Sort.by(Sort.Direction.ASC, sortBy));

        return taskHistories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TaskHistoryDto convertToDto(TaskHistory taskHistory) {
        TaskHistoryDto taskHistoryDto = modelMapper.map(taskHistory, TaskHistoryDto.class);
        taskHistoryDto.setIdTask(taskHistory.getTask().getId());
        return taskHistoryDto;
    }
}

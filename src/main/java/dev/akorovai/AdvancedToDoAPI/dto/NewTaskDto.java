package dev.akorovai.AdvancedToDoAPI.dto;

import dev.akorovai.AdvancedToDoAPI.entity.TaskType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class NewTaskDto {

    private String title;

    private String description;

    private Character priority;

    private LocalDateTime dueDate;


    private Long idCategory;

    private TaskType taskType;

    private Set<SubtaskDto> subtasks;

    private Set<Long> labelIds;

}

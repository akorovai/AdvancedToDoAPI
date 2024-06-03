package dev.akorovai.AdvancedToDoAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.akorovai.AdvancedToDoAPI.entity.TaskType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NewTaskDto {

    private String title;

    private String description;

    private Character priority;

    private LocalDateTime dueDate;


    private Long idCategory;

    private TaskType taskType;

    private Set<SubtaskDto> subtasks;


}

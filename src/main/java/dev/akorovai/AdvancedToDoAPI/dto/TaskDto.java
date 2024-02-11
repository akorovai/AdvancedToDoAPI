package dev.akorovai.AdvancedToDoAPI.dto;

import dev.akorovai.AdvancedToDoAPI.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {


    private Long id;


    private CategoryDto category;


    private String title;


    private String description;


    private Character priority;


    private LocalDateTime dueDate;


    private Status status;


    private TaskType taskType;


    private Set<TaskHistoryDto> histories;


    private Set<SubtaskDto> subtasks;


    private Set<LabelDto> labels;

}

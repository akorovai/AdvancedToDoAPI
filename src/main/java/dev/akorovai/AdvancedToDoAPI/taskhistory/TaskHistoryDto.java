package dev.akorovai.AdvancedToDoAPI.taskhistory;

import dev.akorovai.AdvancedToDoAPI.task.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TaskHistoryDto {
    private Long id;

    private Status newStatus;

    private LocalDateTime timestamp;

    private Long IdTask;

}

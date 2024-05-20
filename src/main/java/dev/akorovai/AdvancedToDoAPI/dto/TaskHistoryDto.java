package dev.akorovai.AdvancedToDoAPI.dto;

import dev.akorovai.AdvancedToDoAPI.entity.Status;
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

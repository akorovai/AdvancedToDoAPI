package dev.akorovai.AdvancedToDoAPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "TaskHistory")
@Data
public class TaskHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @NotNull
    @Column(name = "new_status")
    private Status newStatus;

    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;


}

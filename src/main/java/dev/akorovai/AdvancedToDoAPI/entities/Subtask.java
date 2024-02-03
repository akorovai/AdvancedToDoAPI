package dev.akorovai.AdvancedToDoAPI.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Subtask")
@Data
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private Long id;

    @Column(name = "title", length = 128)
    private String title;

    @Column(name = "status", length = 10)
    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}

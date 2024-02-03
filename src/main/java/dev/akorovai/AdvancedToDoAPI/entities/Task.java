package dev.akorovai.AdvancedToDoAPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Task")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "description", length = 256)
    private String description;

    @Column(name = "priority", length = 1)
    private String priority;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @NotNull
    @Column(name = "status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "task_order_summary", nullable = false)
    private Integer taskOrderSummary;


    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<TaskHistory> histories;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Subtask> subtasks;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Label> labels;
}

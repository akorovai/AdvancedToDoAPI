package dev.akorovai.AdvancedToDoAPI.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Task")
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @NotNull
    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "description", length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "priority", length = 1)
    private Character priority;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @NotNull
    @Column(name = "status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @Column(name = "task_type", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TaskHistory> histories;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
    private Set<Subtask> subtasks;


    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at", columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime modifiedAt;

    public Task(Long id, String title, String description, Category category, Character priority, LocalDateTime dueDate, Status status, TaskType taskType, Set<TaskHistory> histories, Set<Subtask> subtasks, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
        this.taskType = taskType;
        this.histories = histories;
        this.subtasks = subtasks;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler;

public class SubtaskOrderNotCompletedException extends RuntimeException {
    public SubtaskOrderNotCompletedException(Long subtaskId, Long taskId) {
        super("Subtask with ID: " + subtaskId + " in task with ID: " + taskId + " is not completed.");
    }
}
package dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler;

public class SummaryTaskStatusModificationException extends RuntimeException {
    public SummaryTaskStatusModificationException(Long taskId) {
        super("Cannot change status of summary task with ID: " + taskId);
    }
}
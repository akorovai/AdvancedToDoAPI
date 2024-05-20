package dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler;

import dev.akorovai.AdvancedToDoAPI.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ResponseObject> handleTaskNotFoundException(TaskNotFoundException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }

    @ExceptionHandler(TaskTypeNotFoundException.class)
    public ResponseEntity<ResponseObject> handleTaskTypeNotFoundException(TaskTypeNotFoundException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }

    @ExceptionHandler(IncorrectPriorityException.class)
    public ResponseEntity<ResponseObject> handleIncorrectPriorityException(IncorrectPriorityException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObject);
    }

    @ExceptionHandler(InappropriateProgressStatusException.class)
    public ResponseEntity<ResponseObject> inappropriateProgressStatusException(InappropriateProgressStatusException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObject);
    }


    @ExceptionHandler(SummaryTaskStatusModificationException.class)
    public ResponseEntity<ResponseObject> handleSummaryTaskStatusModificationException(SummaryTaskStatusModificationException ex) {
        ResponseObject responseObject = new ResponseObject(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseObject);
    }

    @ExceptionHandler(SubtaskOrderNotCompletedException.class)
    public ResponseEntity<ResponseObject> handleSubtaskOrderNotCompletedException(SubtaskOrderNotCompletedException ex) {
        ResponseObject responseObject = new ResponseObject(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObject);
    }
}

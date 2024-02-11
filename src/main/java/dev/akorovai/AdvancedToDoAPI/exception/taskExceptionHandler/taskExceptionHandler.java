package dev.akorovai.AdvancedToDoAPI.exception.taskExceptionHandler;

import dev.akorovai.AdvancedToDoAPI.exception.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class taskExceptionHandler {

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

}

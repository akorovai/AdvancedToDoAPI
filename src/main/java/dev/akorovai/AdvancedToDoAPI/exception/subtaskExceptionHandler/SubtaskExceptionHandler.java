package dev.akorovai.AdvancedToDoAPI.exception.subtaskExceptionHandler;

import dev.akorovai.AdvancedToDoAPI.dto.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SubtaskExceptionHandler {
    @ExceptionHandler(SubtaskNotFoundException.class)
    public ResponseEntity<ResponseObject> handleSubtaskNotFoundException(SubtaskNotFoundException ex) {
        ResponseObject responseObject = new ResponseObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }
}

package dev.akorovai.AdvancedToDoAPI.exception.labelException;

import dev.akorovai.AdvancedToDoAPI.exception.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LabelExceptionHandler {

    @ExceptionHandler(DuplicateLabelException.class)
    public ResponseEntity<ResponseObject> handleDuplicateLabelException(DuplicateLabelException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObject);
    }

    @ExceptionHandler(EmptyLabelListException.class)
    public ResponseEntity<ResponseObject> handleEmptyLabelListException(EmptyLabelListException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.NO_CONTENT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseObject);
    }

    @ExceptionHandler(LabelNotFoundException.class)
    public ResponseEntity<ResponseObject> handleLabelNotFoundException(LabelNotFoundException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }
}

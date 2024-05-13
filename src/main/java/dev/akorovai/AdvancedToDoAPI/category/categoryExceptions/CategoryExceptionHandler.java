package dev.akorovai.AdvancedToDoAPI.category.categoryExceptions;

import dev.akorovai.AdvancedToDoAPI.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionHandler {

    @ExceptionHandler(EmptyCategoryListException.class)
    public ResponseEntity<ResponseObject> handleEmptyCategoryListException(EmptyCategoryListException ex) {
        ResponseObject responseObject = new ResponseObject(HttpStatus.NO_CONTENT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseObject);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseObject> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ResponseObject responseObject = new ResponseObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ResponseObject> handleDuplicateCategoryException(DuplicateCategoryException ex){
        ResponseObject responseObject = new ResponseObject(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
    }
}

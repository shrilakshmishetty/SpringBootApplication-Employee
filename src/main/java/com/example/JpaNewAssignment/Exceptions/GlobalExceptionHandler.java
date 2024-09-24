package com.example.JpaNewAssignment.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFound.class)
     public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFound emp){
        ErrorResponse e=new ErrorResponse(emp.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInput in){
        ErrorResponse e=new ErrorResponse(in.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

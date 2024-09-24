package com.example.JpaNewAssignment.Exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmployeeNotFound extends RuntimeException{

    public EmployeeNotFound(String msg){
        super(msg);
    }
}

package com.example.JpaNewAssignment.Exceptions;

public class InvalidInput extends RuntimeException{

    public InvalidInput(String msg){
        super(msg);
    }
}
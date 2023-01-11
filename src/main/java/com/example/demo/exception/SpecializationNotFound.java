package com.example.demo.exception;

public class SpecializationNotFound extends RuntimeException{
    public SpecializationNotFound(){
        super("SPECIALIZATION NOT FOUND");
    }
}

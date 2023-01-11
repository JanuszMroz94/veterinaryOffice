package com.example.demo.exception;

public class VetNotFound extends RuntimeException{
    public VetNotFound(){
        super("VET NOT FOUND");
    }
}

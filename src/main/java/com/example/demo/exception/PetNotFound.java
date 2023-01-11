package com.example.demo.exception;

public class PetNotFound extends  RuntimeException{
    public PetNotFound(){
        super("PET NOT FOUND");
    }
}

package com.example.demo.exception;

public class NoAvailableVetsException extends RuntimeException{
    public NoAvailableVetsException(){
        super("NO AVAILABLE VETS");
    }
}

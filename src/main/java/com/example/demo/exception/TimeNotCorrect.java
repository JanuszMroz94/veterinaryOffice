package com.example.demo.exception;

public class TimeNotCorrect extends  RuntimeException{
    public  TimeNotCorrect(){
        super("GIVE PROPER DATE");
    }
}

package com.example.demo.exception;

public class AppointmentNotFound extends RuntimeException{
    public AppointmentNotFound(){
        super("APPOINTMENT NOT FOUND");
    }
}

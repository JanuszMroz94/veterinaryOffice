package com.example.demo.exception;

public class AppointmentAlreadyTaken extends RuntimeException{
    public AppointmentAlreadyTaken() {
        super("DATE NOT AVAILABLE");
    }
}

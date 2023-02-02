package com.example.demo.exception;

public class AppointmentAlreadyTaken extends RuntimeException{
    public AppointmentAlreadyTaken() {
        super("APPOINTMENT DATE IS NOT AVAILABLE");
    }
}

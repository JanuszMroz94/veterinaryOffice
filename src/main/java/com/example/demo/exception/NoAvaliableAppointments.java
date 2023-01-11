package com.example.demo.exception;

public class NoAvaliableAppointments extends RuntimeException{
    public NoAvaliableAppointments(){
        super("NO APPOINTMENTS AVAILABLE");
    }
}

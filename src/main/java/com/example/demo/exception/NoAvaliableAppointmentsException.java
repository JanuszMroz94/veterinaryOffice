package com.example.demo.exception;

public class NoAvaliableAppointmentsException extends RuntimeException{
    public NoAvaliableAppointmentsException(){
        super("NO APPOINTMENTS AVAILABLE");
    }
}

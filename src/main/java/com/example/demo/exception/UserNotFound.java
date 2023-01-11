package com.example.demo.exception;

public class UserNotFound extends RuntimeException{
    public UserNotFound() {
        super("USER NOT FOUND");
    }
}

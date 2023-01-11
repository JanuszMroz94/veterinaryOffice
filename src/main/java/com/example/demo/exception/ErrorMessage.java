package com.example.demo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {

    private String message;
    private int status;
    private LocalDateTime localDateTime = LocalDateTime.now();

    public ErrorMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
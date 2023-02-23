package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorMessage> handlerUserNotFound(UserNotFound ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PetNotFound.class)
    public ResponseEntity<ErrorMessage> handlerPetNotFound(PetNotFound ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VetNotFound.class)
    public ResponseEntity<ErrorMessage> handlerVetNotFound(VetNotFound ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AppointmentNotFound.class)
    public ResponseEntity<ErrorMessage> handlerAppointmentNotFound(AppointmentNotFound ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SpecializationNotFound.class)
    public ResponseEntity<ErrorMessage> handlerSpecializationNotFound(SpecializationNotFound ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAvaliableAppointmentsException.class)
    public ResponseEntity<ErrorMessage> handlerNoAvailableAppointments(NoAvaliableAppointmentsException ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppointmentAlreadyTaken.class)
    public ResponseEntity<ErrorMessage> handlerAppointmentAlreadyTaken(AppointmentAlreadyTaken ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimeNotCorrect.class)
    public ResponseEntity<ErrorMessage> handlerTimeNotCorrect(TimeNotCorrect ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoAvailableVetsException.class)
    public ResponseEntity<ErrorMessage> handlerNoAvailableVetsException(NoAvailableVetsException ex){
        ErrorMessage errorMessage = new ErrorMessage(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }



}
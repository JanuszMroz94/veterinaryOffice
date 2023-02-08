package com.example.demo.controller;

import com.example.demo.dto.VetName;
import com.example.demo.entity.Appointment;
import com.example.demo.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointment")

public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable int id) {
        return new ResponseEntity<>(appointmentService.getAppointment(id), HttpStatus.OK);
    }

    @GetMapping("/checkIfHasAppointment/{id}/{dateTime}")
    public ResponseEntity<String> check(@PathVariable int id, @PathVariable String dateTime) {
        LocalDateTime dateTime2 = LocalDateTime.parse(dateTime);
        return new ResponseEntity<>(appointmentService.checkIfVetHasVisitAtThatTime(id, dateTime2), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/firstAvaliableAppointment")
    public ResponseEntity<Appointment> abc() {
        return new ResponseEntity<>(appointmentService.firstAvailableAppointment(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Appointment> addAppointmentToVet(@RequestParam(name = "id") int id,
                                                           @RequestParam(name = "idU") int idU,
                                                           @RequestParam(name = "idP") int idP,
                                                           @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return new ResponseEntity<>(
                appointmentService.addAppointmentToVet(id, idU, idP, startDate, endDate)
                , HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAll() {
        return new ResponseEntity<>(appointmentService.getAllAppointment(), HttpStatus.OK);
    }

    @GetMapping("/checkVets")
    public ResponseEntity<List<VetName>> checkVets(@RequestParam(name = "startDate")
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                           LocalDateTime startDate) {
        return new ResponseEntity<>(appointmentService.getAvaliableVets(startDate), HttpStatus.OK);
    }

    @GetMapping("/firestAvailableDate")
    public ResponseEntity<LocalDateTime> firstAvailableDate(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(appointmentService.getFirstAvaliableDateCertainVet(id), HttpStatus.OK);
    }

    @GetMapping("/firstAvailableVet")
    public ResponseEntity<List<VetName>> getFirstAvailableVet(){
        return new ResponseEntity<>(appointmentService.firstAvailableVetEarliestHour(), HttpStatus.OK);
    }
}
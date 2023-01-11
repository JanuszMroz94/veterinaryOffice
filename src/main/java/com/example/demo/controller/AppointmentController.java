package com.example.demo.controller;

import com.example.demo.dto.VetName;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Vet;
import com.example.demo.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable int id) {
        return appointmentService.getAppointment(id);
    }

    @GetMapping("/appointment/all")
    public List<Appointment> getAllAppointmnet() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/checkIfHasAppointment/{id}/{dateTime}")
    public String check(@PathVariable int id, @PathVariable String dateTime) {
        LocalDateTime dateTime2 = LocalDateTime.parse(dateTime);
        return appointmentService.checkIfVetHasVisitAtThatTime(id, dateTime2);
    }

    @DeleteMapping("/appointment/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
    }

    @GetMapping("/firstAvaliableAppointment")
    public Appointment abc() {
        return appointmentService.firstAvailableAppointment();
    }

    @PostMapping("/add")
    public Appointment addAppointmentToVet(@RequestParam(name = "id") int id,
                                           @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                           @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return appointmentService.addAppointmentToVet(id, startDate, endDate);
    }

    @GetMapping("/checkVets")
    public List<VetName> checkVets(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        return appointmentService.getAvaliableVets(startDate);
    }

    @GetMapping("/firestAvailableDate")
    public LocalDateTime firstAvailableDate(@RequestParam(name = "id") int id){
        return appointmentService.getFirstAvaliableDateCertainVet(id);
    }
}
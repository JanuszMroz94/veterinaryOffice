package com.example.demo.controller;

import com.example.demo.dto.FirstAvailableVisit;
import com.example.demo.dto.VetName;
import com.example.demo.entity.Appointment;
import com.example.demo.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")

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

    @GetMapping("/checkVets")
    public ResponseEntity<List<VetName>> checkVets(@RequestParam(name = "startDate")
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                           LocalDateTime startDate) {
        return new ResponseEntity<>(appointmentService.getAvailableVetsGivenDateTime(startDate), HttpStatus.OK);
    }

    @GetMapping("/firstAvailableDate")
    public ResponseEntity<LocalDateTime> firstAvailableDate(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(appointmentService.getFirstAvailableDateGivenVet(id), HttpStatus.OK);
    }

    @GetMapping("/firstAvailableVet")
    public ResponseEntity<FirstAvailableVisit> getFirstAvailableVet(){
        return new ResponseEntity<>(appointmentService.firstAvailableVetEarliestHour(), HttpStatus.OK);
    }

    @GetMapping("/allAvailableAppointmentGivenDay")
    public ResponseEntity<List<FirstAvailableVisit>> AppointmentsGivenDay(@RequestParam(name = "day")
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day){
        return new ResponseEntity<>(appointmentService.getAvailableVetsGivenDay(day), HttpStatus.OK);
    }

    @GetMapping("/allAvailableApointmentsGivenVetGivenDay")
    public ResponseEntity<List<FirstAvailableVisit>> AppointmentsGivenVetGivenDay(@RequestParam(name = "day")
                                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
                                                                                  @RequestParam(name = "id") int id){
        return  new ResponseEntity<>(appointmentService.getAvailableGivenVetGivenDay(day,id), HttpStatus.OK);
    }
}
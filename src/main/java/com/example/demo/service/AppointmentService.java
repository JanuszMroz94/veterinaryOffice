package com.example.demo.service;

import com.example.demo.dto.VetName;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Vet;
import com.example.demo.exception.*;
import com.example.demo.repo.AccountRepo;
import com.example.demo.repo.AppointmentRepo;
import com.example.demo.repo.VetRepo;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AccountRepo accountRepo;
    private final AppointmentRepo appointmentRepo;
    private final VetRepo vetRepo;

    private final int ONE_HOUR = 1;

    public AppointmentService(AccountRepo accountRepo, AppointmentRepo appointmentRepo, VetRepo vetRepo) {
        this.accountRepo = accountRepo;
        this.appointmentRepo = appointmentRepo;
        this.vetRepo = vetRepo;
    }

    private void checkIfVetExists(int id) {
        vetRepo.findById(id).orElseThrow(VetNotFound::new);
    }

    private void checkIfAppointmentExists(int id) {
        appointmentRepo.findById(id).orElseThrow(AppointmentNotFound::new);
    }

    private void checkIfAppointmentAlreadyTaken(int id, LocalDateTime dateTime) {
        if (!(appointmentRepo.hasVetAppointmentCertainDate(id, dateTime) == null)) {
            throw new AppointmentAlreadyTaken();
        }
    }

    public String checkIfVetHasVisitAtThatTime(int id, LocalDateTime dateTime) {
        checkIfAppointmentAlreadyTaken(id, dateTime);
        return "Wizyta dostępna";
    }

    public void checkIfDatesAreCorrect(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new TimeNotCorrect();
        }
    }

    public Appointment getAppointment(int id) {
        checkIfAppointmentExists(id);
        return appointmentRepo.findById(id).get();
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public void deleteAppointment(int id) {
        appointmentRepo.deleteById(id);
    }

    public Appointment firstAvailableAppointment() {
        List<Appointment> appointment = appointmentRepo.firstAvaliableAppointment();
        if (appointment.size() == 0) {
            throw new NoAvaliableAppointments();
        }
        return appointment.get(0);
    }

    public Appointment addAppointmentToVet(int id,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate) {
        checkIfVetExists(id);
        checkIfVetHasVisitAtThatTime(id, startDate);
        checkIfDatesAreCorrect(startDate, endDate);

        Vet vet = vetRepo.findById(id).get();

        Appointment appointment = new Appointment();
        appointment.setStartDate(startDate);
        appointment.setEndDate(endDate);
        appointment.setVet(vet);
        return appointmentRepo.save(appointment);
    }
    public List<VetName> getAvaliableVets(LocalDateTime startDate) {
        List<VetName> lista = appointmentRepo.availableVetsCertainDate(startDate);
        return lista;
    }

    public LocalDateTime getFirstAvaliableDateCertainVet(int id) {
        List<Timestamp> lista = appointmentRepo.endDatesOfCertainVet(id);
        List<LocalDateTime> listOfEnds = new ArrayList<>();
        for (Timestamp t : lista) {
            listOfEnds.add(t.toLocalDateTime());
        }
        int i = 1;
        for (LocalDateTime l : listOfEnds) {
            if (l.getHour() + ONE_HOUR == listOfEnds.get(i).getHour()) {
                i++;
            } else {
                return l;
            }
        }
        return listOfEnds.get(i);
    }

}
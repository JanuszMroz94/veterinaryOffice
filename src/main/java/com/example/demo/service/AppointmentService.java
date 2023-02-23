package com.example.demo.service;

import com.example.demo.dto.FirstAvailableVisit;
import com.example.demo.dto.VetName;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Pet;
import com.example.demo.entity.User;
import com.example.demo.entity.Vet;
import com.example.demo.exception.*;
import com.example.demo.repo.AppointmentRepo;
import com.example.demo.repo.PetRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.VetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final VetRepo vetRepo;
    private final UserRepo userRepo;
    private final PetRepo petRepo;

    private final int ONE_HOUR = 1;

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
        return "Appointment available";
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

    public Appointment addAppointmentToVet(int id, int idU, int idP,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate) {
        checkIfVetExists(id);
        checkIfVetHasVisitAtThatTime(id, startDate);
        checkIfDatesAreCorrect(startDate, endDate);

        Vet vet = vetRepo.findById(id).get();
        User user = userRepo.findById(idU).get();
        Pet pet = petRepo.findById(idP).get();

        Appointment appointment = new Appointment();
        appointment.setStartDate(startDate);
        appointment.setEndDate(endDate);
        appointment.setVet(vet);
        appointment.setUser(user);
        appointment.setPet(pet);

        return appointmentRepo.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public void deleteAppointment(int id) {
        appointmentRepo.deleteById(id);
    }

    public List<VetName> getAvailableVetsGivenDateTime(LocalDateTime startDate) {
        if (startDate.getHour()>15 || startDate.getHour()<8) {
            throw new NoAvaliableAppointmentsException();
        }
        List<VetName> lista = appointmentRepo.allVetsCertainDate(startDate);
        return lista;
    }

    public List<FirstAvailableVisit> getAvailableVetsGivenDay(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        LocalDateTime time = startDate.atTime(8,00);
        List<FirstAvailableVisit> result = new ArrayList<>();
        for (int i =0; i <8; i++) {
            List<VetName> listOfVets = appointmentRepo.allVetsCertainDate(time);
            FirstAvailableVisit vetWithHour = new FirstAvailableVisit();
            vetWithHour.setLocalDateTime(time);
            vetWithHour.setVetName(listOfVets);
            result.add(vetWithHour);
            time =time.plusHours(1);
        }
        return result;
    }

    public List<FirstAvailableVisit> getAvailableGivenVetGivenDay(LocalDate startDate, int id) {
        checkIfVetExists(id);
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        LocalDateTime time = startDate.atTime(8,00);
        List<FirstAvailableVisit> result = new ArrayList<>();
        for (int i =0; i <8; i++) {
            List<VetName> listOfVets = appointmentRepo.givenVetsCertainDate(id,time);
            FirstAvailableVisit vetWithHour = new FirstAvailableVisit();
            vetWithHour.setLocalDateTime(time);
            vetWithHour.setVetName(listOfVets);
            result.add(vetWithHour);
            time =time.plusHours(1);
        }
        return result;
    }

    public LocalDateTime getFirstAvailableDateGivenVet(int id) {
        checkIfVetExists(id);
        //if vet has no appointments at all
        List<Timestamp> endDates = appointmentRepo.endDatesOfCertainVet(id);
        LocalDateTime availableTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        if (endDates.get(0) == null || endDates.isEmpty()) {
            LocalTime currentHour = LocalTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS);
            LocalTime openingHour = LocalTime.of(8,0);
            LocalTime closingHour = LocalTime.of(16,0);
            if (currentHour.isAfter(closingHour) || closingHour.isBefore(openingHour)) {
                availableTime = availableTime.plusDays(currentHour.isBefore(openingHour)?0:1).with(openingHour);
            }
            return availableTime;
        }
        //if vet has appointments
        availableTime= Stream.iterate(LocalDateTime.now(),t->t.plusHours(1))
                .filter(t->t.getHour()<16&&t.getHour()>=8)
                .filter(t->appointmentRepo.hasVetAppointmentCertainDate(id,t)==null)
                .findFirst()
                .orElse(null);
        if (availableTime!=null) {
            availableTime = availableTime.truncatedTo(ChronoUnit.HOURS);
        }
        return availableTime;
    }

    public FirstAvailableVisit firstAvailableVetEarliestHour() {
        if (vetRepo.findAll().isEmpty()) {
            throw new NoAvaliableAppointmentsException();
        }
        List<VetName> lista;
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(1);

        if (localDateTime.getHour() > 15) {
            localDateTime = localDateTime.plusDays(1);
            localDateTime = localDateTime.withHour(8);
        } else if (localDateTime.getHour() < 8) {
            localDateTime = localDateTime.withHour(8);
        }

        do {
            lista = appointmentRepo.firstAvailableVetEarliestHour(localDateTime);
            localDateTime = localDateTime.plusHours(1);

        } while (lista.isEmpty());

        localDateTime = localDateTime.minusHours(1);

        FirstAvailableVisit firstAvailableVisit = new FirstAvailableVisit();
        firstAvailableVisit.setVetName(lista);
        firstAvailableVisit.setLocalDateTime(localDateTime.truncatedTo(ChronoUnit.HOURS));

        return firstAvailableVisit;
    }
}
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final VetRepo vetRepo;
    private final UserRepo userRepo;
    private final PetRepo petRepo;
    private final Clock clock;

    private void checkIfVetExists(int id) {
        vetRepo.findById(id).orElseThrow(VetNotFound::new);
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
        return appointmentRepo.findById(id).orElseThrow(AppointmentNotFound::new);
    }

    public Appointment addAppointmentToVet(int id, int idU, int idP,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate) {
        checkIfVetHasVisitAtThatTime(id, startDate);
        checkIfDatesAreCorrect(startDate, endDate);

        Vet vet = vetRepo.findById(id).orElseThrow(VetNotFound::new);
        User user = userRepo.findById(idU).orElseThrow(UserNotFound::new);
        Pet pet = petRepo.findById(idP).orElseThrow(PetNotFound::new);

        Appointment appointment = new Appointment();
        appointment.setStartDate(startDate);
        appointment.setEndDate(endDate);
        appointment.setVet(vet);
        appointment.setUser(user);
        appointment.setPet(pet);

        return appointmentRepo.save(appointment);
    }

    public void deleteAppointment(int id) {
        appointmentRepo.deleteById(id);
    }

    public List<VetName> getAvailableVetsGivenDateTime(LocalDateTime startDate) {
        if (startDate.getHour() > 15 || startDate.getHour() < 8) {
            throw new NoAvaliableAppointmentsException();
        }
        return appointmentRepo.allVetsCertainDate(startDate);
    }

    public List<FirstAvailableVisit> getAvailableVetsGivenDay(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        final LocalDateTime[] time = {startDate.atTime(8, 0)};
        return IntStream.range(0, 8).mapToObj(i -> {
            List<VetName> listOfVets = appointmentRepo.allVetsCertainDate(time[0]);
            FirstAvailableVisit vetWithHour = new FirstAvailableVisit();
            vetWithHour.setLocalDateTime(time[0]);
            vetWithHour.setVetName(listOfVets);
            time[0] = time[0].plusHours(1);
            return vetWithHour;
        }).collect(Collectors.toList());
    }

    public List<FirstAvailableVisit> getAvailableGivenVetGivenDay(LocalDate startDate, int id) {
        checkIfVetExists(id);
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        final LocalDateTime[] time = {startDate.atTime(8, 0)};
        return IntStream.range(0, 8)
                .mapToObj(i -> {
                    List<VetName> listOfVets = appointmentRepo.givenVetsCertainDate(id, time[0]);
                    FirstAvailableVisit vetWithHour = new FirstAvailableVisit();
                    vetWithHour.setLocalDateTime(time[0]);
                    vetWithHour.setVetName(listOfVets);
                    time[0] = time[0].plusHours(1);
                    return vetWithHour;
                })
                .collect(Collectors.toList());
    }

    public LocalDateTime getFirstAvailableDateGivenVet(int id) {
        checkIfVetExists(id);
        //if vet has no appointments at all
        List<Timestamp> endDates = appointmentRepo.endDatesOfCertainVet(id);
        LocalDateTime availableTime = LocalDateTime.now(clock).truncatedTo(ChronoUnit.HOURS);
        if (endDates.get(0)==null) {
            LocalTime currentHour = LocalTime.now(clock).plusHours(1).truncatedTo(ChronoUnit.HOURS);
            LocalTime openingHour = LocalTime.of(8, 0);
            LocalTime closingHour = LocalTime.of(15, 59);
            if (currentHour.isAfter(closingHour) || currentHour.isBefore(openingHour)) {
                availableTime = availableTime.plusDays(currentHour.isBefore(openingHour) ? 0 : 1).with(openingHour);
                return availableTime;
            } else {
                return availableTime.plusHours(1);
            }
        }
        //if vet has appointments
        availableTime = Stream.iterate(LocalDateTime.now(clock).plusHours(1), t -> t.plusHours(1))
                .filter(t -> t.getHour() < 16 && t.getHour() >= 8)
                .filter(t -> appointmentRepo.hasVetAppointmentCertainDate(id, t) == null)
                .findFirst()
                .orElse(null);

        if (availableTime != null) {
            availableTime = availableTime.truncatedTo(ChronoUnit.HOURS);
        }

        return availableTime;
    }

    public FirstAvailableVisit firstAvailableVetEarliestHour() {
        if (vetRepo.findAll().isEmpty()) {
            throw new NoAvaliableAppointmentsException();
        }
        //check if current hour is between 8-16
        LocalDateTime localDateTime = setDateTimeIfExceedsLimit(LocalDateTime.now(clock).plusHours(1));

        AtomicReference<LocalDateTime> availableAppointmentTime = new AtomicReference<>(localDateTime);

        List<VetName> available_vets = Stream
                .iterate(localDateTime, dt -> setDateTimeIfExceedsLimit(dt.plusHours(1)))
                .filter(dt -> !appointmentRepo.firstAvailableVetEarliestHour(dt).isEmpty())
                .peek(availableAppointmentTime::set)
                .findFirst()
                .map(appointmentRepo::firstAvailableVetEarliestHour)
                .orElseThrow(NoAvaliableAppointmentsException::new);
        return new FirstAvailableVisit(availableAppointmentTime.get().truncatedTo(ChronoUnit.HOURS),available_vets);
    }

    private LocalDateTime setDateTimeIfExceedsLimit(LocalDateTime localDateTime) {
        final int CLOSING_HOUR = 15;
        final int OPENING_HOUR = 8;
        if (localDateTime.getHour() > CLOSING_HOUR) {
            localDateTime = localDateTime.plusDays(1).withHour(OPENING_HOUR);
        } else if (localDateTime.getHour() < OPENING_HOUR) {
            localDateTime = localDateTime.withHour(OPENING_HOUR);
        }
        return localDateTime;
    }
}
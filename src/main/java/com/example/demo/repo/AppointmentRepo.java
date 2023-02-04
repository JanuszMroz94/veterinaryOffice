package com.example.demo.repo;

import com.example.demo.dto.VetName;
import com.example.demo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    @Query(value = "SELECT * FROM \"appointment\"  WHERE \"weterynarz\" IS NULL", nativeQuery = true)
    List<Appointment> firstAvaliableAppointment();

    @Query(value = "SELECT vet.\"vet_id\" as vetId, vet.\"name\" as name FROM \"vet\" as vet " +
            "LEFT JOIN \"appointment\" as appointment " +
            "ON vet.\"vet_id\" = appointment.\"weterynarz\" " +
            "WHERE appointment.\"weterynarz\" = ?1 AND HOUR(appointment.\"start_date\")= HOUR(?2)"
            , nativeQuery = true)
    VetName hasVetAppointmentCertainDate(Integer id, LocalDateTime dateTime);

    @Query(value = "SELECT vet.\"vet_id\" as vetId, vet.\"name\" as name FROM \"vet\" as vet " +
            "LEFT JOIN \"appointment\" as appointment " +
            "ON vet.\"vet_id\" = appointment.\"weterynarz\" " +
            "WHERE HOUR(appointment.\"start_date\") <> HOUR(?1) OR appointment.\"start_date\" IS NULL"
            , nativeQuery = true)
    List<VetName> availableVetsCertainDate(LocalDateTime dateTime);

    @Query(value = "SELECT appointment.\"end_date\" " +
            "FROM \"vet\" as vet " +
            "LEFT JOIN \"appointment\" as appointment " +
            "ON vet.\"vet_id\" = appointment.\"weterynarz\" " +
            "WHERE vet.\"vet_id\" = ?1 "
            , nativeQuery = true)
    List<Timestamp> endDatesOfCertainVet(int id);

    @Query(value = "SELECT vet.\"vet_id\" as vetId, vet.\"name\" as name FROM \"vet\" as vet " +
            "LEFT JOIN \"appointment\" as appointment " +
            "ON vet.\"vet_id\" = appointment.\"weterynarz\" " +
            "WHERE HOUR(appointment.\"start_date\") <> HOUR(?1) OR appointment.\"start_date\" IS NULL"
            , nativeQuery = true)
    List<VetName> firstAvaliableDateAnyVet(LocalDateTime dateTime);
}

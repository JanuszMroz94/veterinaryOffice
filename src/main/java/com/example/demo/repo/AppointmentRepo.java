package com.example.demo.repo;

import com.example.demo.dto.VetName;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    @Query(value = "SELECT vet.\"vet_id\" as vetId, vet.\"name\" as name FROM \"vet\" as vet \n" +
            "LEFT JOIN \"appointment\" as appointment\n" +
            "ON vet.\"vet_id\" = appointment.\"weterynarz\" \n" +
            "WHERE appointment.\"weterynarz\" =?1 AND DATE_TRUNC(hour, appointment.\"start_date\")=DATE_TRUNC(hour,CAST(?2 AS timestamp))"
            , nativeQuery = true)
    VetName hasVetAppointmentCertainDate(Integer id, LocalDateTime dateTime);

    @Query(value = "SELECT vet.\"vet_id\" as vetId, vet.\"name\" as name \n" +
            "FROM \"vet\" as vet \n" +
            "WHERE  NOT EXISTS (\n" +
            "SELECT 1 FROM \"appointment\" as appointment \n" +
            "WHERE vet.\"vet_id\" = appointment.\"weterynarz\" AND HOUR(appointment.\"start_date\") = HOUR(?1) OR appointment.\"start_date\" IS NULL  )\n"
            , nativeQuery = true)
    List<VetName> allVetsCertainDate(LocalDateTime dateTime);

    @Query(value = "SELECT vet.\"vet_id\" as vetId, vet.\"name\" as name \n" +
            "FROM \"vet\" as vet \n" +
            "WHERE vet.\"vet_id\"=?1 AND NOT EXISTS (\n" +
            "SELECT 1 FROM \"appointment\" as appointment \n" +
            "WHERE vet.\"vet_id\" = appointment.\"weterynarz\" AND HOUR(appointment.\"start_date\") = HOUR(?2) OR appointment.\"start_date\" IS NULL  )\n"
            , nativeQuery = true)
    List<VetName> givenVetsCertainDate(int id, LocalDateTime dateTime);

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


    @Query(value = "SELECT vet.\"vet_id\" as vetId, vet.\"name\" as name \n" +
            "FROM \"vet\" as vet \n" +
            "WHERE  NOT EXISTS (\n" +
            "SELECT 1 FROM \"appointment\" as appointment \n" +
            "WHERE vet.\"vet_id\" = appointment.\"weterynarz\" AND HOUR(appointment.\"start_date\") = HOUR(?1) OR appointment.\"start_date\" IS NULL  )\n"
            , nativeQuery = true)
    List<VetName> firstAvailableVetEarliestHour(LocalDateTime dateTime);


    List<Appointment> findByVetAndStartDateBetween(Vet vet_not_found, LocalDateTime startDate, LocalDateTime endDate);
}

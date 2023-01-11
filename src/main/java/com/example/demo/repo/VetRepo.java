package com.example.demo.repo;

import com.example.demo.entity.Appointment;
import com.example.demo.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetRepo extends JpaRepository<Vet,Integer> {


    @Query(value = "SELECT * FROM \"vet\"  WHERE \"weterynarz\" IS NULL", nativeQuery = true)
    List<Appointment> firstAvailableAppointment();
}

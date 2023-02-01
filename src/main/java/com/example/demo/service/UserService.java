package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.Specialization;
import com.example.demo.entity.Vet;
import com.example.demo.exception.SpecializationNotFound;
import com.example.demo.exception.UserNotFound;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AccountRepo accountRepo;
    private final SpecializationRepo specializationRepo;
    private final VetRepo vetRepo;


    private void checkIfUserExists(int id) {
        accountRepo.findById(id).orElseThrow(UserNotFound::new);
    }

    private void checkIfSpecializationExists(int id) {
        specializationRepo.findById(id).orElseThrow(SpecializationNotFound::new);
    }

    public User addAccount(User user) {
        return accountRepo.save(user);
    }

    public User getAccount(int id) {
        checkIfUserExists(id);
        return accountRepo.findById(id).orElse(null);
    }

    public List<User> getAllAccount() {
        return accountRepo.findAll();
    }

    public void deleteAccount(int id) {
        checkIfUserExists(id);
        accountRepo.deleteById(id);
    }

    public Specialization addSpecialization(Specialization specialization) {
        return specializationRepo.save(specialization);
    }

    public Specialization getSpecialization(int id) {
        checkIfSpecializationExists(id);
        return specializationRepo.findById(id).get();
    }

    public List<Specialization> getAllSpecialization() {
        return specializationRepo.findAll();
    }

    public void deleteSpecialization(int id) {
        checkIfSpecializationExists(id);
        specializationRepo.deleteById(id);
    }

    public Specialization addSpecializationToAccount(int sid, int id) {
        checkIfUserExists(id);
        checkIfSpecializationExists(id);
        Vet vet = vetRepo.findById(id).get();
        Specialization specialization = specializationRepo.findById(sid).get();
        specialization.getListOfVets().add(vet);
        vet.getSpecializations().add(specialization);
        vetRepo.save(vet);
        return specialization;
    }


}
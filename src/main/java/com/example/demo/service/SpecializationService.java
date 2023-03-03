package com.example.demo.service;

import com.example.demo.entity.Specialization;
import com.example.demo.entity.Vet;
import com.example.demo.exception.SpecializationNotFound;
import com.example.demo.exception.UserNotFound;
import com.example.demo.exception.VetNotFound;
import com.example.demo.repo.SpecializationRepo;
import com.example.demo.repo.VetRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationService {

    private final SpecializationRepo specializationRepo;
    private final VetRepo vetRepo;

    public SpecializationService(SpecializationRepo specializationRepo, VetRepo vetRepo, VetRepo vetRepo1) {
        this.specializationRepo = specializationRepo;
        this.vetRepo = vetRepo;
    }

    private void checkIfSpecializationExists(int id) {
        specializationRepo.findById(id).orElseThrow(SpecializationNotFound::new);
    }

    public Specialization addSpecialization(Specialization specialization) {
        return specializationRepo.save(specialization);
    }

    public Specialization getSpecialization(int id) {
        return specializationRepo.findById(id).orElseThrow(SpecializationNotFound::new);
    }

    public List<Specialization> getAllSpecialization() {
        return specializationRepo.findAll();
    }

    public void deleteSpecialization(int id) {
        checkIfSpecializationExists(id);
        specializationRepo.deleteById(id);
    }

    public Specialization addSpecializationToAccount(int sid, int id) {
        Vet vet = vetRepo.findById(id).orElseThrow(VetNotFound::new);
        Specialization specialization = specializationRepo.findById(sid).orElseThrow(SpecializationNotFound::new);

        specialization.getListOfVets().add(vet);
        vet.getSpecializations().add(specialization);
        vetRepo.save(vet);
        return specialization;
    }
}

package com.example.demo.service;

import com.example.demo.entity.Vet;
import com.example.demo.exception.VetNotFound;
import com.example.demo.repo.VetRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetService {

    private final VetRepo vetRepo;

    public VetService(VetRepo vetRepo) {
        this.vetRepo = vetRepo;
    }

    private void checkIfVetExists(int id) {
        vetRepo.findById(id).orElseThrow(VetNotFound::new);
    }

    public Vet addVet(Vet vet) {
        return vetRepo.save(vet);
    }

    public Vet getVet(int id) {
        checkIfVetExists(id);
        return vetRepo.findById(id).get();
    }

    public List<Vet> getAllVet() {
        return vetRepo.findAll();
    }

    public void deleteVet(int id) {
        checkIfVetExists(id);
        vetRepo.deleteById(id);
    }

}

package com.example.demo.service;

import com.example.demo.entity.Vet;
import com.example.demo.exception.VetNotFound;
import com.example.demo.repo.VetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetRepo vetRepo;

    private void validateVetExists(int id) {
        vetRepo.findById(id).orElseThrow(VetNotFound::new);
    }

    public Vet addVet(Vet vet) {
        return vetRepo.save(vet);
    }

    public Vet getVet(int id) {
        return vetRepo.findById(id).orElseThrow(VetNotFound::new);
    }

    public List<Vet> getAllVets() {
        return vetRepo.findAll();
    }

    public void deleteVet(int id) {
        validateVetExists(id);
        vetRepo.deleteById(id);
    }

}

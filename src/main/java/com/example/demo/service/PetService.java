package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Pet;
import com.example.demo.exception.PetNotFound;
import com.example.demo.repo.AccountRepo;
import com.example.demo.repo.PetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepo petRepo;
    private final AccountRepo accountRepo;

    private void checkIfPetExists(int id) {
        petRepo.findById(id).orElseThrow(PetNotFound::new);
    }

    public Pet getPet(int id) {
        checkIfPetExists(id);
        return petRepo.findById(id).get();
    }

    public List<Pet> getAllPets() {
        return petRepo.findAll();
    }

    public Pet addPet(Pet pet) {
        return petRepo.save(pet);
    }

    public void deletePet(int id) {
        checkIfPetExists(id);
        petRepo.deleteById(id);
    }

    public Pet addPetToAccount(int id, int idP) {
        Account account = accountRepo.findById(id).get();
        Pet pet = petRepo.findById(idP).get();
        pet.setOwner(account);
        return petRepo.save(pet);
    }
}

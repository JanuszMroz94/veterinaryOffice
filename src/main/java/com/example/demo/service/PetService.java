package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.Pet;
import com.example.demo.exception.PetNotFound;
import com.example.demo.exception.UserNotFound;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.PetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepo petRepo;
    private final UserRepo userRepo;

    private void checkIfPetExists(int id) {
        petRepo.findById(id).orElseThrow(PetNotFound::new);
    }

    public Pet getPet(int id) {
        return petRepo.findById(id).orElseThrow(PetNotFound::new);
    }

    public Pet addPet(Pet pet) {
        return petRepo.save(pet);
    }

    public void deletePet(int id) {
        checkIfPetExists(id);
        petRepo.deleteById(id);
    }

    public Pet addPetToAccount(int id, int idP) {
        User user = userRepo.findById(id).orElseThrow(UserNotFound::new);
        Pet pet = petRepo.findById(idP).get();
        pet.setOwner(user);
        return petRepo.save(pet);
    }

    public void addIllness(int id ,String illness) {
        Pet pet = petRepo.findById(id).orElseThrow(PetNotFound::new);
        Set<String> ill = pet.getIllnesses();
        ill.add(illness);
        pet.setIllnesses(ill);
        petRepo.save(pet);
    }
}
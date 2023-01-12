package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet/")
public class PetController {


    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pet/{id}")
    public Pet getPet(@PathVariable int id) {
        return petService.getPet(id);
    }

    @GetMapping("/pet/all")
    public List<Pet> getAllPet() {
        return petService.getAllPets();
    }

    @PostMapping()
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @DeleteMapping("/pet/{id}")
    public void deletePet(@PathVariable int id) {
        petService.deletePet(id);
    }

    @PostMapping("/pet/{id}/{idP}")
    public Pet addPetToAccount(@PathVariable int id, @PathVariable int idP) {
        return petService.addPetToAccount(id, idP);
    }
}

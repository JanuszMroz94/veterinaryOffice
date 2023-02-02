package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")
public class PetController {


    private final PetService petService;

    @GetMapping()
    public Pet getPet(@RequestParam(name = "id") int id) {
        return petService.getPet(id);
    }

    @GetMapping("/all")
    public List<Pet> getAllPet() {
        return petService.getAllPets();
    }

    @PostMapping()
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @DeleteMapping()
    public void deletePet(@RequestParam(name = "id") int id) {
        petService.deletePet(id);
    }

    @PostMapping("/{id}/user/{idP}")
    public Pet addPetToAccount(@PathVariable int id, @PathVariable int idP) {
        return petService.addPetToAccount(id, idP);
    }
}

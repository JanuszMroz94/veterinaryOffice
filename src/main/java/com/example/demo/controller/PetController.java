package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {


    private final PetService petService;

    @GetMapping()
    public ResponseEntity<Pet> getPet(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(petService.getPet(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        return new ResponseEntity<>(petService.addPet(pet), HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletePet(@RequestParam(name = "id") int id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/user/{idP}")
    public ResponseEntity<Pet> addPetToAccount(@PathVariable int id, @PathVariable int idP) {
        return new ResponseEntity<>(petService.addPetToAccount(id, idP), HttpStatus.CREATED);
    }

    @PostMapping("/illness")
    public ResponseEntity<Void> addIllness(@RequestParam(name= "id") int id,
                                         @RequestParam(name="illness") String illness){
        petService.addIllness(id, illness);
        return ResponseEntity.noContent().build();
    }
}

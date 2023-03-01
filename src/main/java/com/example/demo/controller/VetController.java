package com.example.demo.controller;

import com.example.demo.entity.Vet;
import com.example.demo.service.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vets")
public class VetController {

    private final VetService vetService;

    @PostMapping()
    public ResponseEntity<Vet> addVet(@RequestBody Vet vet) {
        return new ResponseEntity<>(vetService.addVet(vet), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vet> getVet(@PathVariable int id) {
        return new ResponseEntity<>(vetService.getVet(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Vet>> getAllVet() {
        return new ResponseEntity<>(vetService.getAllVets(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Void> deleteVet(@PathVariable int id) {
        vetService.deleteVet(id);
        return ResponseEntity.noContent().build();
    }
}
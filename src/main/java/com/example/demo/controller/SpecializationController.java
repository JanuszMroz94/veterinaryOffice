package com.example.demo.controller;

import com.example.demo.entity.Specialization;
import com.example.demo.service.SpecializationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {

    private final SpecializationService specializationService;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping()
    public ResponseEntity<Specialization> addSpec(@RequestBody Specialization specialization) {
        return new ResponseEntity<>(specializationService.addSpecialization(specialization), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Specialization> getSpecialization(@RequestParam(name = "id") int id) {
        return new ResponseEntity<>(specializationService.getSpecialization(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Specialization>> getAllSpecialization() {
        return new ResponseEntity<>(specializationService.getAllSpecialization(), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteSpecialization(@RequestParam(name = "id") int id) {
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{sid}/vet/{id}")
    public ResponseEntity<Specialization> addSpecTOAcc(@PathVariable int sid, @PathVariable int id) {
        return new ResponseEntity<>(specializationService.addSpecializationToAccount(sid, id), HttpStatus.CREATED);
    }
}

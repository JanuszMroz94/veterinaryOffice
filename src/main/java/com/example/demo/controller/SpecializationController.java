package com.example.demo.controller;

import com.example.demo.entity.Specialization;
import com.example.demo.service.SpecializationService;
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
    public Specialization addSpec(@RequestBody Specialization specialization) {
        return specializationService.addSpecialization(specialization);
    }

    @GetMapping()
    public Specialization getSpecialization(@RequestParam(name = "id") int id) {
        return specializationService.getSpecialization(id);
    }

    @GetMapping("/all")
    public List<Specialization> getAllSpecialization() {
        return specializationService.getAllSpecialization();
    }

    @DeleteMapping()
    public void deleteSpecialization(@RequestParam(name = "id") int id) {
        specializationService.deleteSpecialization(id);
    }

    @PostMapping("/{sid}/vet/{id}")
    public Specialization addSpecTOAcc(@PathVariable int sid, @PathVariable int id) {
        return specializationService.addSpecializationToAccount(sid, id);
    }
}

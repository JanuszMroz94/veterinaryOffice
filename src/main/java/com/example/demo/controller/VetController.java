package com.example.demo.controller;

import com.example.demo.entity.Vet;
import com.example.demo.service.VetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vet")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @PostMapping()
    public Vet addVet(@RequestBody Vet vet) {
        return vetService.addVet(vet);
    }

    @GetMapping("/vet/{id}")
    public Vet getVet(@PathVariable int id) {
        return vetService.getVet(id);
    }

    @GetMapping("/vet/all")
    public List<Vet> getAllVet() {
        return vetService.getAllVet();
    }

    @DeleteMapping("/vet/{id}")
    public void deleteVet(@PathVariable int id) {
        vetService.deleteVet(id);
    }
}

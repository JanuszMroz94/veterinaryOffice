package com.example.demo.controller;

import com.example.demo.entity.Vet;
import com.example.demo.service.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vet")
public class VetController {

    private final VetService vetService;

    @PostMapping()
    public Vet addVet(@RequestBody Vet vet) {
        return vetService.addVet(vet);
    }

    @GetMapping("/{id}")
    public Vet getVet(@PathVariable int id) {
        return vetService.getVet(id);
    }

    @GetMapping("/all")
    public List<Vet> getAllVet() {
        return vetService.getAllVet();
    }

    @DeleteMapping("/{id}")
    public void deleteVet(@PathVariable int id) {
        vetService.deleteVet(id);
    }
}
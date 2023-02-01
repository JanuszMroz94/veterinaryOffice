package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Specialization;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final UserService userService;

    @PostMapping
    public User addAccount(@RequestBody User user) {
        return userService.addAccount(user);
    }

    @GetMapping
    public User getAccount(@RequestParam(name="id") int id) {
        return userService.getAccount(id);
    }

    @GetMapping("/all")
    public List<User> getAllAccount() {
        return userService.getAllAccount();
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable int id) {
        userService.deleteAccount(id);
    }

    @PostMapping("/specialization")
    public Specialization addSpec(@RequestBody Specialization specialization) {
        return userService.addSpecialization(specialization);
    }

    @GetMapping("/specialization/{id}")
    public Specialization getSpecialization(@PathVariable int id) {
        return userService.getSpecialization(id);
    }

    @GetMapping("/specialization/all")
    public List<Specialization> getAllSpecialization() {
        return userService.getAllSpecialization();
    }

    @DeleteMapping("/specialization/{id}")
    public void deleteSpecialization(@PathVariable int id) {
        userService.deleteSpecialization(id);
    }

    @PostMapping("/specialization/{sid}/account/{id}")
    public Specialization addSpecTOAcc(@PathVariable int sid, @PathVariable int id) {
        return userService.addSpecializationToAccount(sid, id);
    }


}
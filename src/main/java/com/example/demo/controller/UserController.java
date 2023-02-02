package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

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

    @DeleteMapping()
    public void deleteAccount(@RequestParam(name = "id") int id) {
        userService.deleteAccount(id);
    }





}
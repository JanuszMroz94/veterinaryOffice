package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> addAccount(@RequestBody User user) {
        return new ResponseEntity<>(userService.addAccount(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<User> getAccount(@RequestParam(name="id") int id) {
        return new ResponseEntity<>(userService.getAccount(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllAccount() {
        return new ResponseEntity<>(userService.getAllAccount(), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAccount(@RequestParam(name = "id") int id) {
        userService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
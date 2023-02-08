package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFound;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final SpecializationRepo specializationRepo;
    private final VetRepo vetRepo;


    private void checkIfUserExists(int id) {
        userRepo.findById(id).orElseThrow(UserNotFound::new);
    }

    public User addAccount(User user) {
        return userRepo.save(user);
    }

    public User patchAccount(int id, User user){
        User user1 = userRepo.findById(id).get();
        return userRepo.save(user1);
    }

    public User getAccount(int id) {
        checkIfUserExists(id);
        return userRepo.findById(id).orElse(null);
    }

    public List<User> getAllAccount() {
        return userRepo.findAll();
    }

    public void deleteAccount(int id) {
        checkIfUserExists(id);
        userRepo.deleteById(id);
    }
}
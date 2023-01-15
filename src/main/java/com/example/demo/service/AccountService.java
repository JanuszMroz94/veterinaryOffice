package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Specialization;
import com.example.demo.exception.SpecializationNotFound;
import com.example.demo.exception.UserNotFound;
import com.example.demo.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepo accountRepo;
    private final SpecializationRepo specializationRepo;


    private void checkIfUserExists(int id) {
        accountRepo.findById(id).orElseThrow(UserNotFound::new);
    }

    private void checkIfSpecializationExists(int id) {
        specializationRepo.findById(id).orElseThrow(SpecializationNotFound::new);
    }

    public Account addAccount(Account account) {
        return accountRepo.save(account);
    }

    public Account getAccount(int id) {
        checkIfUserExists(id);
        return accountRepo.findById(id).orElse(null);
    }

    public List<Account> getAllAccount() {
        return accountRepo.findAll();
    }

    public void deleteAccount(int id) {
        checkIfUserExists(id);
        accountRepo.deleteById(id);
    }

    public Specialization addSpecialization(Specialization specialization) {
        return specializationRepo.save(specialization);
    }

    public Specialization getSpecialization(int id) {
        checkIfSpecializationExists(id);
        return specializationRepo.findById(id).get();
    }

    public List<Specialization> getAllSpecialization() {
        return specializationRepo.findAll();
    }

    public void deleteSpecialization(int id) {
        checkIfSpecializationExists(id);
        specializationRepo.deleteById(id);
    }

    public Specialization addSpecializationToAccount(int sid, int id) {
        checkIfUserExists(id);
        checkIfSpecializationExists(id);
        Account account = accountRepo.findById(id).get();
        Specialization specialization = specializationRepo.findById(sid).get();
        specialization.getListOfUsers().add(account);
        account.getSpecializations().add(specialization);
        accountRepo.save(account);
        return specialization;
    }


}
package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Specialization;
import com.example.demo.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account addAccount(@RequestBody Account account) {
        return accountService.addAccount(account);
    }

    @GetMapping("/all")
    public List<Account> getAllAccount() {
        return accountService.getAllAccount();
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable int id) {
        return accountService.getAccount(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
    }

    @PostMapping("/specialization")
    public Specialization addSpec(@RequestBody Specialization specialization) {
        return accountService.addSpecialization(specialization);
    }

    @GetMapping("/specialization/all")
    public List<Specialization> getAllSpecialization() {
        return accountService.getAllSpecialization();
    }

    @GetMapping("/specialization/{id}")
    public Specialization getSpecialization(@PathVariable int id) {
        return accountService.getSpecialization(id);
    }

    @DeleteMapping("/specialization/{id}")
    public void deleteSpecialization(@PathVariable int id) {
        accountService.deleteSpecialization(id);
    }

    @PostMapping("/specialization/{sid}/account/{id}")
    public Specialization addSpecTOAcc(@PathVariable int sid, @PathVariable int id) {
        return accountService.addSpecializationToAccount(sid, id);
    }


}
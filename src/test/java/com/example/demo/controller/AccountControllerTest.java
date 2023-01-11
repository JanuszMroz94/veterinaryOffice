package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.repo.AccountRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    AccountRepo accountRepo;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clearDB() {
        accountRepo.deleteAll();
    }

    @AfterEach
    void clearDBAfter() {
        accountRepo.deleteAll();
    }

    @Test
    void shouldCheckIfAccountWasCreated() throws Exception {
        //given
        Account account = new Account();
        account.setName("John");
        //when
        mockMvc.perform(post("http://localhost:8080/api/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        List<Account> all = accountRepo.findAll();
        assertThat(all).isNotNull();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getName()).isEqualTo("John");
    }
}
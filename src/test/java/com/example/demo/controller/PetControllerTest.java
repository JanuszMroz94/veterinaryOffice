package com.example.demo.controller;

import com.example.demo.entity.Pet;
import com.example.demo.repo.PetRepo;
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
public class PetControllerTest {

    @Autowired
    PetRepo petRepo;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clearDB(){
        petRepo.deleteAll();
    }

    @AfterEach()
    void clearDBAfter(){
        petRepo.deleteAll();
    }

    @Test
    void shouldCheckIfPetWasCreated() throws Exception{
        //given
        Pet pet = new Pet();
        pet.setName("Azor");
        //when
        mockMvc.perform(post("http://localhost:8080/api/pet/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pet)))
                .andDo(print())
                .andExpect(status().isCreated());
        //then
        List<Pet> all = petRepo.findAll();
        assertThat(all).isNotNull();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getName()).isEqualTo("Azor");
    }
}

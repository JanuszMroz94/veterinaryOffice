package com.example.demo.controller;

import com.example.demo.entity.Vet;
import com.example.demo.repo.VetRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.aspectj.lang.annotation.After;
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
public class VetControllerTest {

    @Autowired
    VetRepo vetRepo;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void clearDB(){
        vetRepo.deleteAll();
    }

    @AfterEach
    void clearDBAfter(){
        vetRepo.deleteAll();
    }

    @Test
    void shouldCheckIfVetWasCreated() throws Exception{
        //given
        Vet vet = new Vet();
        vet.setName("Dr. Dolittle");
        //when
        mockMvc.perform(post("http://localhost:8080/api/vets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vet)))
                .andDo(print())
                .andExpect(status().isCreated());
        //then
        List<Vet> all = vetRepo.findAll();
        assertThat(all).isNotNull();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getName()).isEqualTo("Dr. Dolittle");



    }
}

package com.example.demo.controller;


import com.example.demo.entity.Vet;
import com.example.demo.repo.AppointmentRepo;
import com.example.demo.repo.VetRepo;
import com.example.demo.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AppointmentService appointmentService;
    @Autowired
    AppointmentRepo appointmentRepo;
    @Autowired
    VetRepo vetRepo;
    @Autowired
    ObjectMapper objectMapper;
    @BeforeEach
    void clearDB(){
        appointmentRepo.deleteAll();
    }
    @AfterEach
    void clearDBAfter(){
        appointmentRepo.deleteAll();
    }
    @Test
    void shouldCheckIfFirstAvailableVisitIsFound() throws Exception{
        //given
        String instantExpected = "2014-12-22T10:15:30Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        Instant instant = Instant.now(clock);

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(instant);
            Instant now = Instant.now();
            assertThat(now.toString()).isEqualTo(instantExpected);
        }

        Vet vet1 = new Vet();
        vet1.setName("Vet1");
        vetRepo.save(vet1);

        mockMvc.perform(get("http://localhost:8080/api/appointment/firstAvailableVet"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"localDateTime\":\"2023-02-10T08:00:00\",\"vetName\":[{\"name\":\"Vet1\",\"vetId\":1}]}"));


        //then
    }
}

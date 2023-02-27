package com.example.demo.service.appointmentServiceTest;

import com.example.demo.entity.Vet;
import com.example.demo.repo.AppointmentRepo;
import com.example.demo.repo.VetRepo;
import com.example.demo.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class GetFirstAvailableDateGivenVetTest {

    // mock your tested class
    @InjectMocks
    private AppointmentService appointmentService;

    //Mock your clock bean
    @Mock
    private Clock clock;

    @Mock
    AppointmentRepo appointmentRepo;
    @Mock
    VetRepo vetRepo;

//    field that will contain the fixed clock
//    private Clock fixedClock;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetFirstAvailableDateGivenVetWhenHourIsBefore8(){
        //given
        final LocalDate LOCAL_DATE = LocalDate.of(2000,1,1);
        Clock fixedClock;
        fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimestamp = new ArrayList<>();
        when(appointmentRepo.endDatesOfCertainVet(eq(0))).thenReturn(emptyTimestamp);
        LocalDateTime expected = LocalDateTime.of(2000,1,1,8,0);

        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testGetFirstAvailableDateGivenVetWhenHourIsAfter16(){
        //given
        final LocalDate LOCAL_DATE = LocalDate.of(2000,1,1);
        Clock fixedClock;
        fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(17).plusMinutes(30)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();


        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimestamp = new ArrayList<>();
        when(appointmentRepo.endDatesOfCertainVet(0)).thenReturn(emptyTimestamp);
        LocalDateTime expected = LocalDateTime.of(2000,1,2,8,0);

        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testGetFirstAvailableDateGivenVetWhenHourIs12(){
        //given
        final LocalDate LOCAL_DATE = LocalDate.of(2000,1,1);
        Clock fixedClock;
        fixedClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(12)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimestamp = new ArrayList<>();
        when(appointmentRepo.endDatesOfCertainVet(0)).thenReturn(emptyTimestamp);
        LocalDateTime expected = LocalDateTime.of(2000,1,1,13,0);

        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }
}
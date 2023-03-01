package com.example.demo.service.appointmentServiceTest;

import com.example.demo.dto.VetName;
import com.example.demo.entity.Vet;
import com.example.demo.exception.VetNotFound;
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
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class GetFirstAvailableDateGivenVetTest {

    // mock tested class
    @InjectMocks
    private AppointmentService appointmentService;

    //Mock clock bean
    @Mock
    private Clock clock;

    @Mock
    AppointmentRepo appointmentRepo;
    @Mock
    VetRepo vetRepo;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void shouldReturnHour8SameDayIfNoAppointmentsAndHourIsBefore8() {
        //given
        final LocalDate fixedTime = LocalDate.of(2000, 1, 1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimestamp = new ArrayList<>();
        emptyTimestamp.add(null);
        when(appointmentRepo.endDatesOfCertainVet(eq(0))).thenReturn(emptyTimestamp);

        LocalDateTime expected = LocalDateTime.of(2000, 1, 1, 8, 0);
        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnHour8NextDayIfnoAppointmentsWhenHourIsAfter16() {
        //given
        final LocalDate fixedTime = LocalDate.of(2000, 1, 1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(17).plusMinutes(30)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();


        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimestamp = new ArrayList<>();
        emptyTimestamp.add(null);
        when(appointmentRepo.endDatesOfCertainVet(0)).thenReturn(emptyTimestamp);

        LocalDateTime expected = LocalDateTime.of(2000, 1, 2, 8, 0);
        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnHour13SameDayIfNoAppointmentsWhenHourIs12() {
        //given
        final LocalDate fixedTime = LocalDate.of(2000, 1, 1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(12)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimestamp = new ArrayList<>();
        emptyTimestamp.add(null);
        when(appointmentRepo.endDatesOfCertainVet(0)).thenReturn(emptyTimestamp);

        LocalDateTime expected = LocalDateTime.of(2000, 1, 1, 13, 0);
        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnHour8NextDayIfNoAppointmentsWhenHourIs15() {
        //given
        final LocalDate fixedTime = LocalDate.of(2000, 1, 1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(15)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimeStamp = new ArrayList<>();
        emptyTimeStamp.add(null);
        when(appointmentRepo.endDatesOfCertainVet(0)).thenReturn(emptyTimeStamp);

        LocalDateTime expected = LocalDateTime.of(2000, 1, 2, 8, 0);
        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shouldReturnHour9SameDayIfNoAppointmentWhenHourIs8() {
        //given
        final LocalDate fixedTime = LocalDate.of(2000, 1, 1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(8)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> emptyTimestamp = new ArrayList<>();
        emptyTimestamp.add(null);
        when(appointmentRepo.endDatesOfCertainVet(0)).thenReturn(emptyTimestamp);

        LocalDateTime expected = LocalDateTime.of(2000, 1, 1, 9, 0);
        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void shoudlReturnHour13SameDayIfHasAppointmentAt12WhenHourIs11() {
        //given
        final LocalDate fixedTime = LocalDate.of(2000, 1, 1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(11)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        List<Timestamp> appointmentTimestamp = List.of(Timestamp.valueOf("2000-01-01 13:00:00"));

        when(appointmentRepo.endDatesOfCertainVet(0)).thenReturn(appointmentTimestamp);
        when(appointmentRepo.hasVetAppointmentCertainDate(0, LocalDateTime.of(
                2000
                , 1
                , 1
                , 12
                , 0))).thenReturn(new VetName() {
            @Override
            public int getVetId() {
                return 0;
            }

            @Override
            public String getName() {
                return null;
            }
        });

        LocalDateTime expected = LocalDateTime.of(2000, 1, 1, 13, 0);
        //when
        LocalDateTime result = appointmentService.getFirstAvailableDateGivenVet(0);
        //then
        assertThat(result).isEqualTo(expected);
    }
    @Test
    public void shouldThrowExceptionIfThereIsNoVet(){
        //given
        //when
        Throwable throwable = catchThrowable(()-> appointmentService.getFirstAvailableDateGivenVet(0));
        //then
        assertThat(throwable).isInstanceOf(VetNotFound.class);

    }

}
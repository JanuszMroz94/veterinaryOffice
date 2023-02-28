package com.example.demo.service.appointmentServiceTest;

import com.example.demo.dto.FirstAvailableVisit;
import com.example.demo.dto.VetName;
import com.example.demo.entity.Vet;
import com.example.demo.exception.NoAvaliableAppointmentsException;
import com.example.demo.repo.AppointmentRepo;
import com.example.demo.repo.VetRepo;
import com.example.demo.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class FirstAvailableVetEarliestHourTest {
    @InjectMocks
    private AppointmentService appointmentService;

    //Mock clock bean
    @Mock
    private Clock clock;
    @Mock
    private AppointmentRepo appointmentRepo;
    @Mock
    VetRepo vetRepo;

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void HourIs8am() {
        //given
        final LocalDate fixedTime = LocalDate.of(2000,1,1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(8)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        List<VetName> vetNameList = new ArrayList<>();
        vetNameList.add(0, new VetName() {
            @Override
            public int getVetId() {
                return 1;
            }

            @Override
            public String getName() {
                return "";
            }
        });
        List<Vet> vetList = new ArrayList<>();
        vetList.add(new Vet());
        when(vetRepo.findAll()).thenReturn(vetList);
        when(appointmentRepo.firstAvailableVetEarliestHour(LocalDateTime.of(2000,1,1,9,0))).thenReturn(vetNameList);

        FirstAvailableVisit expected = new FirstAvailableVisit(LocalDateTime.of(2000,1,1,9,0), vetNameList );
        //when
        FirstAvailableVisit result = appointmentService.firstAvailableVetEarliestHour();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void HourIs10am(){
        //given
        LocalDate fixedTime = LocalDate.of(2000,1,1);
        Clock fixedClock;
        fixedClock = Clock.fixed(fixedTime.atStartOfDay(ZoneId.systemDefault()).toInstant().plus(Duration.ofHours(10)), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        List<VetName> vetNameList = new ArrayList<>();
        vetNameList.add(0, new VetName() {
            @Override
            public int getVetId() {
                return 1;
            }

            @Override
            public String getName() {
                return "";
            }
        });
        List<Vet> vetList = new ArrayList<>();
        vetList.add(new Vet());
        when(vetRepo.findAll()).thenReturn(vetList);
        when(appointmentRepo.firstAvailableVetEarliestHour(LocalDateTime.of(2000,1,1,11,0))).thenReturn(vetNameList);

        FirstAvailableVisit expected = new FirstAvailableVisit(LocalDateTime.of(2000,1,1,11,0), vetNameList);
        //when
        FirstAvailableVisit result = appointmentService.firstAvailableVetEarliestHour();
        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void noVetsInDB(){
        //given
        //when
        Throwable throwable = catchThrowable(()-> appointmentService.firstAvailableVetEarliestHour());
        //then
        assertThat(throwable).isInstanceOf(NoAvaliableAppointmentsException.class);
    }
}

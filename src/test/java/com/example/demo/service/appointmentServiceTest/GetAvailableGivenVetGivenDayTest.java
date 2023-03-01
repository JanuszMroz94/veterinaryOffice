package com.example.demo.service.appointmentServiceTest;

import com.example.demo.dto.FirstAvailableVisit;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class GetAvailableGivenVetGivenDayTest {
    @InjectMocks
    private AppointmentService appointmentService;
    @Mock
    private AppointmentRepo appointmentRepo;
    @Mock
    private VetRepo vetRepo;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnExceptionIfNoVets() {
        //given
        LocalDate exampleDate = LocalDate.of(2000, 1, 1);
        //when
        Throwable throwable = catchThrowable(() -> appointmentService.getAvailableGivenVetGivenDay(exampleDate, 0));
        //then
        assertThat(throwable).isInstanceOf(VetNotFound.class);
    }

    @Test
    public void shouldReturnExceptionIfDateIsNull(){
        //given
        LocalDate nullDate = null;
        //when
        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        Throwable throwable = catchThrowable(()->appointmentService.getAvailableGivenVetGivenDay(nullDate,0));
        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnListOfFirstAvailableVisit(){
        //given
        when(vetRepo.findById(0)).thenReturn(Optional.of(new Vet()));
        final LocalDate exampleDay = LocalDate.of(2000,1,1);
        LocalDateTime exampleDateTime = LocalDateTime.of(2000,1,1,8,0);

        List<VetName> vetNameList = List.of(new VetName() {
            @Override
            public int getVetId() {
                return 0;
            }

            @Override
            public String getName() {
                return null;
            }
        });
        FirstAvailableVisit firstAvailableVisit = new FirstAvailableVisit(exampleDateTime, vetNameList);

        List<FirstAvailableVisit> expected = new ArrayList<>();
        expected.add(firstAvailableVisit);

        FirstAvailableVisit firstAvailableVisit1 = new FirstAvailableVisit();
        firstAvailableVisit1.setLocalDateTime(LocalDateTime.of(2000,1,1,9,0));
        expected.add(firstAvailableVisit1);
        firstAvailableVisit1.setVetName(Collections.emptyList());

        when(appointmentRepo.givenVetsCertainDate(0, LocalDateTime.of(2000,1,1,8,0)))
                .thenReturn(vetNameList);

        //when
        List<FirstAvailableVisit> result =appointmentService.getAvailableGivenVetGivenDay(exampleDay,0);
        //then
        assertThat(result.get(0)).isEqualTo(expected.get(0));
    }
}

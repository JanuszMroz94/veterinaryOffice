package com.example.demo.service.appointmentServiceTest;

import com.example.demo.dto.FirstAvailableVisit;
import com.example.demo.dto.VetName;
import com.example.demo.repo.AppointmentRepo;
import com.example.demo.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

public class GetAvailableVetsGivenDayTest {

    @InjectMocks
    private AppointmentService appointmentService;
    @Mock
    private AppointmentRepo appointmentRepo;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldThrowExceptionIfStartDateIsNull() {
        //given
        LocalDate nullDate = null;
        //when
        Throwable throwable = catchThrowable(() -> appointmentService.getAvailableVetsGivenDay(nullDate));
        //then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnListOfAvailableVisitsAtGivenDay() {
        //given
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        VetName vetName = new VetName() {
            @Override
            public int getVetId() {
                return 0;
            }

            @Override
            public String getName() {
                return null;
            }
        };
        List<VetName> vetNameList = List.of(vetName);
        FirstAvailableVisit firstAvailableVisit = new FirstAvailableVisit(startDate.atTime(8, 0), vetNameList);
        List<FirstAvailableVisit> expected = List.of(firstAvailableVisit);
        when(appointmentRepo.allVetsCertainDate(startDate.atTime(8, 0))).thenReturn(vetNameList);
        //when
        List<FirstAvailableVisit> result = appointmentService.getAvailableVetsGivenDay(startDate);
        //then
        assertThat(result.get(0)).isEqualTo(expected.get(0));

    }

}

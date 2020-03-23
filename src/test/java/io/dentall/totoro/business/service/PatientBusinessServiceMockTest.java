package io.dentall.totoro.business.service;

import io.dentall.totoro.business.vm.PatientSearchVM;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientBusinessServiceMockTest {

    @Mock
    private PatientRepository mockPatientRepository;

    @InjectMocks
    private PatientBusinessService patientBusinessService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransformROCDateToLocalDate() {
        String rocDate = "1001010";
        assertThat(DateTimeUtil.transformROCDateToLocalDate(rocDate)).isEqualTo(LocalDate.of(2011, 10, 10));
    }

    @Test
    public void testGetAge() {
        LocalDate rocDate = DateTimeUtil.transformROCDateToLocalDate("1001010");

        assertThat(DateTimeUtil.getAge(rocDate, LocalDate.of(2020, 1, 1))).isEqualTo(8);
        assertThat(DateTimeUtil.getAge(rocDate, LocalDate.of(2020, 10, 10))).isEqualTo(9);
        assertThat(DateTimeUtil.getAge(rocDate, LocalDate.of(2021, 7, 8))).isEqualTo(9);
    }

    @Test
    public void testFindByMedicalId() {
        Mockito.when(mockPatientRepository.findByMedicalId("1-02", Pageable.unpaged())).thenReturn(
            new PageImpl<>(Collections.singletonList(
                new PatientSearchVM(null, null, "00001-02", LocalDate.of(1999, 1, 1))
            ))
        );

        PatientSearchVM patientSearchVM = patientBusinessService
            .findByMedicalId("01-02", Pageable.unpaged())
            .stream()
            .findFirst()
            .get();

        assertThat(patientSearchVM.getMedicalId()).isEqualTo("00001-02");
    }
}

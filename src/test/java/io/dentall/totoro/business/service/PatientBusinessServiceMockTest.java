package io.dentall.totoro.business.service;

import io.dentall.totoro.business.vm.PatientSearchVM;
import io.dentall.totoro.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    public void testFindByMedicalId() {
        Mockito.when(mockPatientRepository.findByMedicalId("102", Pageable.unpaged())).thenReturn(
            new PageImpl<>(Collections.singletonList(
                new PatientSearchVM(null, null, "0001-02")
            ))
        );

        PatientSearchVM patientSearchVM = patientBusinessService
            .findByMedicalId("00102", Pageable.unpaged())
            .stream()
            .findFirst()
            .get();

        assertThat(patientSearchVM.getMedicalId()).isEqualTo("0001-02");
    }
}

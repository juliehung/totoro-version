package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityDoctor;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityPatient;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.PatientResourceIntTest;
import io.dentall.totoro.web.rest.UserResourceIntTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiAbnormalityServiceIntTest {

    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");

    @Autowired
    private EntityManager em;

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NhiExtendPatientRepository nhiExtendPatientRepository;

    @Autowired
    private DisposalRepository disposalRepository;

    private NhiAbnormalityService nhiAbnormalityService;

    private Patient patient;

    private User user;

    @Before
    public void setup() {
        nhiAbnormalityService = new NhiAbnormalityService(nhiExtendDisposalRepository, patientRepository, userRepository);

        user = UserResourceIntTest.createEntity(em);
        user.setLogin("abc");
        user.setEmail("abc@abc.com");
        userRepository.save(user);

        patient = PatientResourceIntTest.createEntity(em);
        patient.setLastDoctor(user.getExtendUser());
        patientRepository.save(patient);
        patient.setMedicalId(String.format("%05d", patient.getId()));
        patient.setNhiExtendPatient(nhiExtendPatientRepository.save(new NhiExtendPatient().patient(patient).cardNumber("123456")));
    }

    @Test
    @Transactional
    public void testGetDoctorsByCode() {
        LocalDate date = OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toLocalDate();

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure1 = new NhiExtendTreatmentProcedure().a73("9999C");
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure2 = new NhiExtendTreatmentProcedure().a73("9999C");

        Disposal disposal = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal);
        disposal.setCreatedBy(user.getLogin());

        NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + dayFormatter.format(date))
            .patientId(patient.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Arrays.asList(nhiExtendTreatmentProcedure1, nhiExtendTreatmentProcedure2)))
            .disposal(disposal)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal);
        nhiExtendTreatmentProcedure1.setNhiExtendDisposal(nhiExtendDisposal);
        nhiExtendTreatmentProcedure2.setNhiExtendDisposal(nhiExtendDisposal);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNot(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get(),
                NhiExtendDisposalUploadStatus.NONE
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByCode(nhiExtendDisposals, "9999C", 1);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user.getId());
        assertThat(nhiAbnormalityDoctor.getCount()).isEqualTo(2);
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(1);
        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor.getPatients().get(0);
        assertThat(nhiAbnormalityPatient.getId()).isEqualTo(patient.getId());
        assertThat(nhiAbnormalityPatient.getDate()).isEqualTo(nhiExtendDisposal.getDate());
    }

    @Test
    @Transactional
    public void testGetDoctorsByFrequency() {
        LocalDate date = OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toLocalDate();

        Disposal disposal1 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal1);
        disposal1.setCreatedBy(user.getLogin());
        NhiExtendDisposal nhiExtendDisposal1 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "10")
            .patientId(patient.getId())
            .disposal(disposal1)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal1);

        Disposal disposal2 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal2);
        disposal2.setCreatedBy(user.getLogin());
        NhiExtendDisposal nhiExtendDisposal2 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "20")
            .patientId(patient.getId())
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNot(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get(),
                NhiExtendDisposalUploadStatus.NONE
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByFrequency(nhiExtendDisposals, 1);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user.getId());
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(1);
        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor.getPatients().get(0);
        assertThat(nhiAbnormalityPatient.getId()).isEqualTo(patient.getId());
        assertThat(nhiAbnormalityPatient.getCount()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void testGetDoctorsByRatio() {
        LocalDate date = OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toLocalDate();

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure1 = new NhiExtendTreatmentProcedure().a73("90015C").a74("1122");
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure2 = new NhiExtendTreatmentProcedure().a73("90015C").a74("3344");
        Disposal disposal1 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal1);
        disposal1.setCreatedBy(user.getLogin());
        NhiExtendDisposal nhiExtendDisposal1 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "20")
            .patientId(patient.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Arrays.asList(nhiExtendTreatmentProcedure1, nhiExtendTreatmentProcedure2)))
            .disposal(disposal1)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal1);
        nhiExtendTreatmentProcedure1.setNhiExtendDisposal(nhiExtendDisposal1);
        nhiExtendTreatmentProcedure2.setNhiExtendDisposal(nhiExtendDisposal1);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure3 = new NhiExtendTreatmentProcedure().a73("90004C").a74("1144");
        Disposal disposal2 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal2);
        disposal2.setCreatedBy(user.getLogin());
        NhiExtendDisposal nhiExtendDisposal2 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "01")
            .patientId(patient.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure3)))
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);
        nhiExtendTreatmentProcedure3.setNhiExtendDisposal(nhiExtendDisposal2);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNot(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get(),
                NhiExtendDisposalUploadStatus.NONE
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByRatioOf90004cTo90015c(nhiExtendDisposals, 0.2);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user.getId());
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(1);
        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor.getPatients().get(0);
        assertThat(nhiAbnormalityPatient.getId()).isEqualTo(patient.getId());
        assertThat(nhiAbnormalityPatient.getRatioOf90004cTo90015c().get("11")).isEqualTo(0.25);
        assertThat(nhiAbnormalityPatient.getRatioOf90004cTo90015c().get("44")).isEqualTo(0.25);
    }
}

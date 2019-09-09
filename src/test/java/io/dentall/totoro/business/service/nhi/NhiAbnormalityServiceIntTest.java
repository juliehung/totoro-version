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

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private TreatmentDrugRepository treatmentDrugRepository;

    private NhiAbnormalityService nhiAbnormalityService;

    private Patient patient1;

    private User user1;

    private Patient patient2;

    private User user2;

    private LocalDate date;

    @Before
    public void setup() {
        nhiAbnormalityService = new NhiAbnormalityService(nhiExtendDisposalRepository, patientRepository, userRepository);

        user1 = UserResourceIntTest.createEntity(em);
        user1.setLogin("abc");
        user1.setEmail("abc@abc.com");
        userRepository.save(user1);

        patient1 = PatientResourceIntTest.createEntity(em);
        patient1.setLastDoctor(user1.getExtendUser());
        patientRepository.save(patient1);
        patient1.setMedicalId(String.format("%05d", patient1.getId()));
        patient1.setNhiExtendPatient(nhiExtendPatientRepository.save(new NhiExtendPatient().patient(patient1).cardNumber("12345")));

        user2 = UserResourceIntTest.createEntity(em);
        user2.setLogin("def");
        user2.setEmail("def@def.com");
        userRepository.save(user2);

        patient2 = PatientResourceIntTest.createEntity(em);
        patient2.setLastDoctor(user2.getExtendUser());
        patientRepository.save(patient2);
        patient2.setMedicalId(String.format("%05d", patient2.getId()));
        patient2.setNhiExtendPatient(nhiExtendPatientRepository.save(new NhiExtendPatient().patient(patient2).cardNumber("67890")));

        date = OffsetDateTime.now(TimeConfig.ZONE_OFF_SET).toLocalDate();
    }

    @Test
    @Transactional
    public void testGetDoctorsByCode() {
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure1 = new NhiExtendTreatmentProcedure().a73("9999C");
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure2 = new NhiExtendTreatmentProcedure().a73("9999C");

        Disposal disposal = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal);
        disposal.setCreatedBy(user1.getLogin());

        NhiExtendDisposal nhiExtendDisposal = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + dayFormatter.format(date))
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Arrays.asList(nhiExtendTreatmentProcedure1, nhiExtendTreatmentProcedure2)))
            .disposal(disposal)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal);
        nhiExtendTreatmentProcedure1.setNhiExtendDisposal(nhiExtendDisposal);
        nhiExtendTreatmentProcedure2.setNhiExtendDisposal(nhiExtendDisposal);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get()
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByCode(nhiExtendDisposals, "9999C");

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor.getCount()).isEqualTo(2);
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(1);
        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor.getPatients().get(0);
        assertThat(nhiAbnormalityPatient.getId()).isEqualTo(patient1.getId());
        assertThat(nhiAbnormalityPatient.getDate()).isEqualTo(nhiExtendDisposal.getDate());
    }

    @Test
    @Transactional
    public void testGetDoctorsByFrequency() {
        Disposal disposal1 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal1);
        disposal1.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal1 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "10")
            .patientId(patient1.getId())
            .disposal(disposal1)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal1);

        Disposal disposal2 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal2);
        disposal2.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal2 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "20")
            .patientId(patient1.getId())
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get()
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByFrequency(nhiExtendDisposals);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(1);
        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor.getPatients().get(0);
        assertThat(nhiAbnormalityPatient.getId()).isEqualTo(patient1.getId());
        assertThat(nhiAbnormalityPatient.getCount()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void testGetDoctorsByRatioOf90004cTo90015c() {
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure1 = new NhiExtendTreatmentProcedure().a73("90015C").a74("1122");
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure2 = new NhiExtendTreatmentProcedure().a73("90015C").a74("3344");
        Disposal disposal1 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal1);
        disposal1.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal1 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "20")
            .a19("2")
            .a54(date.getYear() - 1911 + monthFormatter.format(date) + "10")
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Arrays.asList(nhiExtendTreatmentProcedure1, nhiExtendTreatmentProcedure2)))
            .disposal(disposal1)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal1);
        nhiExtendTreatmentProcedure1.setNhiExtendDisposal(nhiExtendDisposal1);
        nhiExtendTreatmentProcedure2.setNhiExtendDisposal(nhiExtendDisposal1);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure3 = new NhiExtendTreatmentProcedure().a73("90004C").a74("1144");
        Disposal disposal2 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal2);
        disposal2.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal2 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "01")
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure3)))
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);
        nhiExtendTreatmentProcedure3.setNhiExtendDisposal(nhiExtendDisposal2);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get()
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByRatioOf90004cTo90015c(nhiExtendDisposals);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(1);
        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor.getPatients().get(0);
        assertThat(nhiAbnormalityPatient.getId()).isEqualTo(patient1.getId());
        assertThat(nhiAbnormalityPatient.getRatioOf90004cTo90015c().get("11")).isEqualTo(0.25);
        assertThat(nhiAbnormalityPatient.getRatioOf90004cTo90015c().get("22")).isEqualTo(0.0);
        assertThat(nhiAbnormalityPatient.getRatioOf90004cTo90015c().get("33")).isEqualTo(0.0);
        assertThat(nhiAbnormalityPatient.getRatioOf90004cTo90015c().get("44")).isEqualTo(0.25);
    }

    @Test
    @Transactional
    public void testGetDoctorsBy92013cAvgPoint() {
        Drug drug1 = drugRepository.save(new Drug().name("drug1").price(50.0));
        Drug drug2 = drugRepository.save(new Drug().name("drug2").price(10.0));

        TreatmentDrug treatmentDrug1 = treatmentDrugRepository.save(new TreatmentDrug().drug(drug1));
        TreatmentDrug treatmentDrug2 = treatmentDrugRepository.save(new TreatmentDrug().drug(drug2));

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure1 = new NhiExtendTreatmentProcedure().a73("92013C");
        NhiExtendTreatmentDrug nhiExtendTreatmentDrug1 = new NhiExtendTreatmentDrug().a77("2.0").a78("01").treatmentDrug(treatmentDrug1);
        NhiExtendTreatmentDrug nhiExtendTreatmentDrug2 = new NhiExtendTreatmentDrug().a77("8.0").a78("02").treatmentDrug(treatmentDrug2);
        Disposal disposal1 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal1);
        disposal1.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal1 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + dayFormatter.format(date))
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure1)))
            .nhiExtendTreatmentDrugs(new HashSet<>(Arrays.asList(nhiExtendTreatmentDrug1, nhiExtendTreatmentDrug2)))
            .disposal(disposal1)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal1);
        nhiExtendTreatmentProcedure1.setNhiExtendDisposal(nhiExtendDisposal1);
        nhiExtendTreatmentDrug1.setNhiExtendDisposal(nhiExtendDisposal1);
        nhiExtendTreatmentDrug2.setNhiExtendDisposal(nhiExtendDisposal1);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure2 = new NhiExtendTreatmentProcedure().a73("92013C");
        NhiExtendTreatmentDrug nhiExtendTreatmentDrug3 = new NhiExtendTreatmentDrug().a77("2.0").a78("01").treatmentDrug(treatmentDrug2);
        Disposal disposal2 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal2);
        disposal2.setCreatedBy(user1.getLogin());
        LocalDate nextMonth = date.plusMonths(1);
        NhiExtendDisposal nhiExtendDisposal2 = new NhiExtendDisposal()
            .a17(nextMonth.getYear() - 1911 + monthFormatter.format(nextMonth) + "01")
            .a19("2")
            .a54(date.getYear() - 1911 + monthFormatter.format(date) + dayFormatter.format(date))
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure2)))
            .nhiExtendTreatmentDrugs(new HashSet<>(Collections.singletonList(nhiExtendTreatmentDrug3)))
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);
        nhiExtendTreatmentProcedure2.setNhiExtendDisposal(nhiExtendDisposal2);
        nhiExtendTreatmentDrug3.setNhiExtendDisposal(nhiExtendDisposal2);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure3 = new NhiExtendTreatmentProcedure().a73("92013C");
        NhiExtendTreatmentDrug nhiExtendTreatmentDrug4 = new NhiExtendTreatmentDrug().a77("1.0").a78("01").treatmentDrug(treatmentDrug1);
        Disposal disposal3 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal3);
        disposal3.setCreatedBy(user2.getLogin());
        NhiExtendDisposal nhiExtendDisposal3 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + dayFormatter.format(date))
            .patientId(patient2.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure3)))
            .nhiExtendTreatmentDrugs(new HashSet<>(Collections.singletonList(nhiExtendTreatmentDrug4)))
            .disposal(disposal3)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal3);
        nhiExtendTreatmentProcedure3.setNhiExtendDisposal(nhiExtendDisposal3);
        nhiExtendTreatmentDrug4.setNhiExtendDisposal(nhiExtendDisposal3);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get()
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsBy92013cAvgPoint(nhiExtendDisposals);

        assertThat(doctors).hasSize(2);

        NhiAbnormalityDoctor nhiAbnormalityDoctor1 = doctors.get(0);
        assertThat(nhiAbnormalityDoctor1.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor1.getPatients()).hasSize(1);
        assertThat(nhiAbnormalityDoctor1.getPoint()).isEqualTo(100.0);
        NhiAbnormalityPatient nhiAbnormalityPatient1 = nhiAbnormalityDoctor1.getPatients().get(0);
        assertThat(nhiAbnormalityPatient1.getCode92013cPoint()).isEqualTo(200.0);

        NhiAbnormalityDoctor nhiAbnormalityDoctor2 = doctors.get(1);
        assertThat(nhiAbnormalityDoctor2.getId()).isEqualTo(user2.getId());
        assertThat(nhiAbnormalityDoctor2.getPatients()).hasSize(1);
        assertThat(nhiAbnormalityDoctor2.getPoint()).isEqualTo(50.0);
        NhiAbnormalityPatient nhiAbnormalityPatient2 = nhiAbnormalityDoctor2.getPatients().get(0);
        assertThat(nhiAbnormalityPatient2.getCode92013cPoint()).isEqualTo(50.0);
    }
}

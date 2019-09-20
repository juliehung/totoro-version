package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.vm.nhi.NhiAbnormality;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityDoctor;
import io.dentall.totoro.business.vm.nhi.NhiAbnormalityPatient;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private TreatmentProcedureRepository treatmentProcedureRepository;

    @Autowired
    private NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    @Autowired
    private NhiProcedureRepository nhiProcedureRepository;

    private NhiAbnormalityService nhiAbnormalityService;

    private Patient patient1;

    private User user1;

    private Patient patient2;

    private User user2;

    private LocalDate date;

    @Before
    public void setup() {
        nhiAbnormalityService = new NhiAbnormalityService(nhiExtendDisposalRepository, patientRepository, userRepository, nhiProcedureRepository);

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
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByCode(nhiExtendDisposals, "9999C", x -> true);

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
            .examinationPoint(100)
            .patientId(patient1.getId())
            .disposal(disposal1)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal1);

        Disposal disposal2 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal2);
        disposal2.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal2 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "20")
            .examinationPoint(150)
            .patientId(patient1.getId())
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get()
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByFrequency(nhiExtendDisposals, 1);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(2);
        YearMonth ym = YearMonth.of(date.getYear(), date.getMonth());
        List<NhiAbnormalityPatient> patients = nhiAbnormalityDoctor.getPatients().stream().sorted(Comparator.comparing(NhiAbnormalityPatient::getDate)).collect(Collectors.toList());
        NhiAbnormalityPatient nhiAbnormalityPatient1 = patients.get(0);
        assertThat(nhiAbnormalityPatient1.getId()).isEqualTo(patient1.getId());
        assertThat(nhiAbnormalityPatient1.getCount()).isEqualTo(2);
        assertThat(nhiAbnormalityPatient1.getDate()).isEqualTo(ym.atDay(10));
        NhiAbnormalityPatient nhiAbnormalityPatient2 = patients.get(1);
        assertThat(nhiAbnormalityPatient2.getId()).isEqualTo(patient1.getId());
        assertThat(nhiAbnormalityPatient2.getCount()).isEqualTo(2);
        assertThat(nhiAbnormalityPatient2.getDate()).isEqualTo(ym.atDay(20));
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
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByCode92013cAvgPoint(nhiExtendDisposals);

        assertThat(doctors).hasSize(2);

        NhiAbnormalityDoctor nhiAbnormalityDoctor1 = doctors.get(0);
        assertThat(nhiAbnormalityDoctor1.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor1.getPatients()).hasSize(1);
        assertThat(nhiAbnormalityDoctor1.getPoint()).isEqualTo(100.0);
        NhiAbnormalityPatient nhiAbnormalityPatient1 = nhiAbnormalityDoctor1.getPatients().get(0);
        assertThat(nhiAbnormalityPatient1.getCode92013cPoint()).isEqualTo(
            Double.parseDouble(nhiExtendTreatmentDrug1.getA77()) * nhiExtendTreatmentDrug1.getTreatmentDrug().getDrug().getPrice() +
                Double.parseDouble(nhiExtendTreatmentDrug2.getA77()) * nhiExtendTreatmentDrug2.getTreatmentDrug().getDrug().getPrice() +
                Double.parseDouble(nhiExtendTreatmentDrug3.getA77()) * nhiExtendTreatmentDrug3.getTreatmentDrug().getDrug().getPrice()
        );

        NhiAbnormalityDoctor nhiAbnormalityDoctor2 = doctors.get(1);
        assertThat(nhiAbnormalityDoctor2.getId()).isEqualTo(user2.getId());
        assertThat(nhiAbnormalityDoctor2.getPatients()).hasSize(1);
        assertThat(nhiAbnormalityDoctor2.getPoint()).isEqualTo(50.0);
        NhiAbnormalityPatient nhiAbnormalityPatient2 = nhiAbnormalityDoctor2.getPatients().get(0);
        assertThat(nhiAbnormalityPatient2.getCode92013cPoint()).isEqualTo(
            Double.parseDouble(nhiExtendTreatmentDrug4.getA77()) * nhiExtendTreatmentDrug4.getTreatmentDrug().getDrug().getPrice()
        );
    }

    @Test
    @Transactional
    public void testGetDoctorsBy92013cAvgPointExcludeCategoryA3AndCode92063c() {
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
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL)
            .category("A3");
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);
        nhiExtendTreatmentProcedure2.setNhiExtendDisposal(nhiExtendDisposal2);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure3 = new NhiExtendTreatmentProcedure().a73("92013C");
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure4 = new NhiExtendTreatmentProcedure().a73("92063C");
        Disposal disposal3 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal3);
        disposal3.setCreatedBy(user2.getLogin());
        NhiExtendDisposal nhiExtendDisposal3 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + dayFormatter.format(date))
            .patientId(patient2.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Arrays.asList(nhiExtendTreatmentProcedure3, nhiExtendTreatmentProcedure4)))
            .disposal(disposal3)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal3);
        nhiExtendTreatmentProcedure3.setNhiExtendDisposal(nhiExtendDisposal3);
        nhiExtendTreatmentProcedure4.setNhiExtendDisposal(nhiExtendDisposal3);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get()
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByCode92013cAvgPoint(nhiExtendDisposals);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor1 = doctors.get(0);
        assertThat(nhiAbnormalityDoctor1.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor1.getPatients()).hasSize(1);
        assertThat(nhiAbnormalityDoctor1.getPoint()).isEqualTo(180.0);
        NhiAbnormalityPatient nhiAbnormalityPatient1 = nhiAbnormalityDoctor1.getPatients().get(0);
        assertThat(nhiAbnormalityPatient1.getCode92013cPoint()).isEqualTo(
            Double.parseDouble(nhiExtendTreatmentDrug1.getA77()) * nhiExtendTreatmentDrug1.getTreatmentDrug().getDrug().getPrice() +
                Double.parseDouble(nhiExtendTreatmentDrug2.getA77()) * nhiExtendTreatmentDrug2.getTreatmentDrug().getDrug().getPrice()
        );
    }

    @Test
    @Transactional
    public void testGetDoctorsByRatioOf90004cTo90015cExcludeDoctor() {
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

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure4 = new NhiExtendTreatmentProcedure().a73("90015C").a74("1122");
        Disposal disposal3 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal3);
        disposal2.setCreatedBy(user2.getLogin());
        NhiExtendDisposal nhiExtendDisposal3 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "15")
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure4)))
            .disposal(disposal3)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal3);
        nhiExtendTreatmentProcedure4.setNhiExtendDisposal(nhiExtendDisposal3);

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
    public void testGetDoctorsByFrequencyExcludeSpecificCodeF7AndExaminationPoint() {
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure1 = new NhiExtendTreatmentProcedure().a73("1111C");
        Disposal disposal1 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal1);
        disposal1.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal1 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "10")
            .examinationPoint(250)
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure1)))
            .disposal(disposal1)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal1);
        nhiExtendTreatmentProcedure1.setNhiExtendDisposal(nhiExtendDisposal1);

        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure2 = new NhiExtendTreatmentProcedure().a73("testC");
        Disposal disposal2 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal2);
        disposal2.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal2 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "20")
            .examinationPoint(50)
            .patientId(patient1.getId())
            .nhiExtendTreatmentProcedures(new HashSet<>(Collections.singletonList(nhiExtendTreatmentProcedure2)))
            .disposal(disposal2)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal2);
        nhiExtendTreatmentProcedure2.setNhiExtendDisposal(nhiExtendDisposal2);

        Disposal disposal3 = new Disposal().status(DisposalStatus.PERMANENT);
        disposalRepository.save(disposal3);
        disposal1.setCreatedBy(user1.getLogin());
        NhiExtendDisposal nhiExtendDisposal3 = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + "01")
            .examinationPoint(0)
            .patientId(patient1.getId())
            .disposal(disposal3)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);
        nhiExtendDisposalRepository.save(nhiExtendDisposal3);

        List<NhiExtendDisposal> nhiExtendDisposals = nhiExtendDisposalRepository
            .findByDateBetweenAndUploadStatusNotNone(
                DateTimeUtil.localMonthFirstDay.get(),
                DateTimeUtil.localMonthLastDay.get()
            );
        List<NhiAbnormalityDoctor> doctors = nhiAbnormalityService.getDoctorsByFrequency(nhiExtendDisposals, 0);

        assertThat(doctors).hasSize(1);
        NhiAbnormalityDoctor nhiAbnormalityDoctor = doctors.get(0);
        assertThat(nhiAbnormalityDoctor.getId()).isEqualTo(user1.getId());
        assertThat(nhiAbnormalityDoctor.getPatients()).hasSize(1);
        NhiAbnormalityPatient nhiAbnormalityPatient = nhiAbnormalityDoctor.getPatients().get(0);
        assertThat(nhiAbnormalityPatient.getId()).isEqualTo(patient1.getId());
        assertThat(nhiAbnormalityPatient.getCount()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void testGet048() {
        final String code1 = "89013C";
        NhiAbnormality nhiAb = new NhiAbnormality();
        YearMonth ym = YearMonth.now();

        createRelativeDataInMemo(date, patient1.getId(), user1.getLogin(), code1, "1122");
        createRelativeDataInMemo(date, patient1.getId(), user1.getLogin(), code1, "11");
        List<NhiExtendDisposal> r = nhiExtendDisposalRepository.findAll();
        nhiAbnormalityService.get048(r, nhiAb, ym);
        assertThat(nhiAb.getStatisticNo048()).isEqualTo(0.0d);

        createRelativeDataInMemo(date, patient2.getId(), user1.getLogin(), code1, "11");
        r = nhiExtendDisposalRepository.findAll();
        nhiAbnormalityService.get048(r, nhiAb, ym);
        assertThat(nhiAb.getStatisticNo048()).isEqualTo(1d / 4d);

        createRelativeDataInMemo(date.minusDays(700), patient2.getId(), user1.getLogin(), code1, "11");
        nhiAbnormalityService.get048(r, nhiAb, ym);
        assertThat(nhiAb.getStatisticNo048()).isEqualTo(2d / 5d);

        createRelativeDataInMemo(date.minusDays(780), patient2.getId(), user1.getLogin(), code1, "11");
        nhiAbnormalityService.get048(r, nhiAb, ym);
        assertThat(nhiAb.getStatisticNo048()).isEqualTo(2d / 5d);
    }

    @Test
    @Transactional
    public void testNhiDisposalWithA73AndPatientId() {
        final String code = "878787";

        createRelativeDataInMemo(date, patient1.getId(), user1.getLogin(), code, "1122");

        // Test
        List<NhiExtendDisposal> r = nhiExtendDisposalRepository.findByDateBetweenAndUploadStatusNotAndPatientId(
            date.minusDays(2),
            date.plusDays(2),
            patient1.getId()
        );
        assertThat(r.size()).isEqualTo(1);

        List<NhiExtendDisposal> rr = nhiExtendDisposalRepository.findByDateBetweenAndUploadStatusNotAndPatientId(
            date.minusDays(2),
            date.plusDays(2),
            patient2.getId()
        );
        assertThat(rr.size()).isEqualTo(0);
    }

    private void createRelativeDataInMemo(LocalDate date, Long patId, String doc, String code, String toothPos) {
        // Dis
        Disposal dis = new Disposal().status(DisposalStatus.PERMANENT);
        dis.setCreatedBy(doc);

        // Nhi dis
        NhiExtendDisposal nhiDis = new NhiExtendDisposal()
            .a17(date.getYear() - 1911 + monthFormatter.format(date) + dayFormatter.format(date))
            .patientId(patId)
            .uploadStatus(NhiExtendDisposalUploadStatus.NORMAL);

        // Tx
        TreatmentProcedure tx = new TreatmentProcedure().status(TreatmentProcedureStatus.COMPLETED);

        // Nhi tx
        NhiExtendTreatmentProcedure nhiTx = new NhiExtendTreatmentProcedure().a73(code).a74(toothPos);

        // Relation
        HashSet<NhiExtendDisposal> nhiDisSet = new HashSet<>();
        HashSet<TreatmentProcedure> txSet = new HashSet<>();
        HashSet<NhiExtendTreatmentProcedure> nhiTxSet = new HashSet<>();
        nhiTxSet.add(nhiTx);
        nhiDisSet.add(nhiDis);
        txSet.add(tx);

        dis.setNhiExtendDisposals(nhiDisSet);
        dis.setTreatmentProcedures(txSet);

        tx.setDisposal(dis);
        tx.setNhiExtendTreatmentProcedure(nhiTx);

        nhiTx.setTreatmentProcedure(tx);
        nhiTx.setNhiExtendDisposal(nhiDis);

        nhiDis.setDisposal(dis);
        nhiDis.setNhiExtendTreatmentProcedures(nhiTxSet);

        // Save
        disposalRepository.save(dis);
        treatmentProcedureRepository.save(tx);
        nhiExtendDisposalRepository.save(nhiDis);
        nhiExtendTreatmentProcedureRepository.save(nhiTx);
    }
}

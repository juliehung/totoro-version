package io.dentall.totoro.service;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.NhiExtendPatientRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.PatientResourceIntTest;
import io.dentall.totoro.web.rest.TreatmentProcedureResourceIntTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
@TestPropertySource("classpath:nhi_rule.csv")
public class NhiServiceIntTest {

    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");

    @Autowired
    private EntityManager em;

    @Autowired
    private TreatmentProcedureRepository treatmentProcedureRepository;

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Autowired
    private NhiExtendPatientRepository nhiExtendPatientRepository;

    @Autowired
    private NhiExtendDisposalService nhiExtendDisposalService;

    @Autowired
    private NhiExtendPatientService nhiExtendPatientService;

    @Autowired
    private PatientService patientService;

    private NhiService nhiService;

    @Before
    public void setup() {
        nhiService = new NhiService(nhiExtendDisposalRepository, nhiExtendPatientRepository);
    }

    @Test
    public void testCheckXRay() {
        NhiExtendTreatmentProcedure xRayFalse = new NhiExtendTreatmentProcedure().a73("XRayFalse").check("");
        nhiService.checkXRay.accept(xRayFalse);
        assertThat(xRayFalse.getCheck()).isEqualTo("");

        NhiExtendTreatmentProcedure xRayTrue = new NhiExtendTreatmentProcedure().a73("XRayTrue").check("");
        nhiService.checkXRay.accept(xRayTrue);
        assertThat(xRayTrue.getCheck()).isEqualTo(xRayTrue.getA73() + " 需要檢附 X 片\n");
    }

    @Test
    public void testCheckMedicalRecord() {
        NhiExtendTreatmentProcedure medicalRecordFalse = new NhiExtendTreatmentProcedure().a73("MedicalRecordFalse").check("");
        nhiService.checkMedicalRecord.accept(medicalRecordFalse);
        assertThat(medicalRecordFalse.getCheck()).isEqualTo("");

        NhiExtendTreatmentProcedure medicalRecordTrue = new NhiExtendTreatmentProcedure().a73("MedicalRecordTrue").check("");
        nhiService.checkMedicalRecord.accept(medicalRecordTrue);
        assertThat(medicalRecordTrue.getCheck()).isEqualTo(medicalRecordTrue.getA73() + " 病歷須記載\n");
    }

    @Test
    public void testCheckExclude() {
        NhiExtendTreatmentProcedure exclude91012C = new NhiExtendTreatmentProcedure().a73("Exclude91012C").check("");
        NhiExtendTreatmentProcedure nhi89001C = new NhiExtendTreatmentProcedure().a73("89001C");
        NhiExtendTreatmentProcedure nhi91012C = new NhiExtendTreatmentProcedure().a73("91012C");

        nhiService.checkExclude.accept(exclude91012C, Stream.of(exclude91012C, nhi89001C, nhi91012C).collect(Collectors.toSet()));
        assertThat(exclude91012C.getCheck()).isEqualTo(exclude91012C.getA73() + " 不得與 " + nhi91012C.getA73() + " 同時申報\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalLessOrEqualThan() {
        LocalDate dateBefore100 = DateTimeUtil.getGreaterThanEqualDate.apply(100);
        LocalDate dateBefore20 = DateTimeUtil.getGreaterThanEqualDate.apply(20);
        //tx 1
        TreatmentProcedure treatmentProcedureDateBefore100 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore100 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore100.getYear() - 1911 + monthFormatter.format(dateBefore100) + dayFormatter.format(dateBefore100))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(new NhiExtendTreatmentProcedure().a73("LessOrEqualThan180").treatmentProcedure(treatmentProcedureDateBefore100)));
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore100);

        NhiExtendTreatmentProcedure lessOrEqualThan180 = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("LessOrEqualThan180")
            .check("");

        // tx2
        TreatmentProcedure treatment2ProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposal2DateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("LessOrEqualThan180")
                    .treatmentProcedure(treatment2ProcedureDateBefore20)
                )
            );

        nhiExtendDisposalService.save(nhiExtDisposal2DateBefore20);

        nhiService.checkInterval.accept(lessOrEqualThan180);
        assertThat(lessOrEqualThan180.getCheck()).isEqualTo(lessOrEqualThan180.getA73() + " 180 天內不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalMonth() {
        LocalDate monthFirstDay = DateTimeUtil.localMonthFirstDay.get();
        TreatmentProcedure treatmentProcedureMonthFirstDay = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalMonthFirstDay = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(monthFirstDay.getYear() - 1911 + monthFormatter.format(monthFirstDay) + dayFormatter.format(monthFirstDay))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(new NhiExtendTreatmentProcedure().a73("Month1").treatmentProcedure(treatmentProcedureMonthFirstDay)));
        nhiExtendDisposalService.save(nhiExtDisposalMonthFirstDay);

        NhiExtendTreatmentProcedure month = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("Month1")
            .check("");
        nhiService.checkInterval.accept(month);
        assertThat(month.getCheck()).isEqualTo(month.getA73() + " 1 個月內不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalYear() {
        LocalDate yearFirstDay = DateTimeUtil.localYearFirstDay.get();
        TreatmentProcedure treatmentProcedureYearFirstDay = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalYearFirstDay = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(yearFirstDay.getYear() - 1911 + monthFormatter.format(yearFirstDay) + dayFormatter.format(yearFirstDay))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(new NhiExtendTreatmentProcedure().a73("Year2").treatmentProcedure(treatmentProcedureYearFirstDay)));
        nhiExtendDisposalService.save(nhiExtDisposalYearFirstDay);

        LocalDate yearSecondDay = DateTimeUtil.localYearFirstDay.get().plusDays(1);
        TreatmentProcedure treatmentProcedureYearSecondDay = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalYearSecondDay = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(yearFirstDay.getYear() - 1911 + monthFormatter.format(yearSecondDay) + dayFormatter.format(yearSecondDay))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(new NhiExtendTreatmentProcedure().a73("Year2").treatmentProcedure(treatmentProcedureYearSecondDay)));
        nhiExtendDisposalService.save(nhiExtDisposalYearSecondDay);

        NhiExtendTreatmentProcedure year = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("Year2")
            .check("");
        nhiService.checkInterval.accept(year);
        assertThat(year.getCheck()).isEqualTo(year.getA73() + " 年度不得重複申報 2 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalMonthWithQuadrants() {
        LocalDate monthFirstDay = DateTimeUtil.localMonthFirstDay.get();
        TreatmentProcedure treatmentProcedureMonthFirstDay = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalMonthFirstDay = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(monthFirstDay.getYear() - 1911 + monthFormatter.format(monthFirstDay) + dayFormatter.format(monthFirstDay))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("Month1Q1")
                    .a74("FM")
                    .treatmentProcedure(treatmentProcedureMonthFirstDay)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposalMonthFirstDay);

        NhiExtendTreatmentProcedure month = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("Month1Q1")
            .a74("11")
            .check("");
        nhiService.checkInterval.accept(month);
        assertThat(month.getCheck()).isEqualTo(month.getA73() + " 同象限不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalLessOrEqualThanWithPermanentTeeth() {
        LocalDate dateBefore20 = DateTimeUtil.getGreaterThanEqualDate.apply(20);
        TreatmentProcedure treatmentProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("LessOrEqualThan30x2PT1")
                    .a74("FM")
                    .treatmentProcedure(treatmentProcedureDateBefore20)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore20);

        NhiExtendTreatmentProcedure lessOrEqualThan30x2T1 = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("LessOrEqualThan30x2PT1")
            .a74("11")
            .check("");
        nhiService.checkInterval.accept(lessOrEqualThan30x2T1);
        assertThat(lessOrEqualThan30x2T1.getCheck()).isEqualTo(lessOrEqualThan30x2T1.getA73() + " 同顆牙不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalSameHospital() {
        LocalDate dateBefore365 = DateTimeUtil.getGreaterThanEqualDate.apply(365);
        TreatmentProcedure treatmentProcedureDateBefore365 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore365 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore365.getYear() - 1911 + monthFormatter.format(dateBefore365) + dayFormatter.format(dateBefore365))
            .a14("XYZ")
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(new NhiExtendTreatmentProcedure().a73("SameHospital").treatmentProcedure(treatmentProcedureDateBefore365)));
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore365);

        NhiExtendTreatmentProcedure sameHospital = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .a14("XYZ")
                .patientId(1L)
            )
            .a73("SameHospital")
            .check("");
        nhiService.checkInterval.accept(sameHospital);
        assertThat(sameHospital.getCheck()).isEqualTo(sameHospital.getA73() + " 同一院所不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalLessOrEqualThanWithDeciduousTeeth() {
        LocalDate dateBefore20 = DateTimeUtil.getGreaterThanEqualDate.apply(20);
        TreatmentProcedure treatmentProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("LessOrEqualThan365DT1")
                    .a74("55")
                    .treatmentProcedure(treatmentProcedureDateBefore20)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore20);

        NhiExtendTreatmentProcedure lessOrEqualThan365DT1 = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("LessOrEqualThan365DT1")
            .a74("55")
            .check("");
        nhiService.checkInterval.accept(lessOrEqualThan365DT1);
        assertThat(lessOrEqualThan365DT1.getCheck()).isEqualTo(lessOrEqualThan365DT1.getA73() + " 同顆牙不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalLessOrEqualThanWithIdentity() {
        LocalDate dateBefore20 = DateTimeUtil.getGreaterThanEqualDate.apply(20);
        // Tx 1
        TreatmentProcedure treatmentProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("SevereIdentity")
                    .treatmentProcedure(treatmentProcedureDateBefore20)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore20);

        // tx2
        TreatmentProcedure treatment2ProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposal2DateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("SevereIdentity")
                    .treatmentProcedure(treatment2ProcedureDateBefore20)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposal2DateBefore20);

        NhiExtendTreatmentProcedure severeIdentity = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
                .patientIdentity("001")
            )
            .a73("SevereIdentity")
            .check("");
        nhiService.checkInterval.accept(severeIdentity);
        assertThat(severeIdentity.getCheck()).isEqualTo(severeIdentity.getA73() + " 60 天內不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalLessOrEqualThanWithConjunction() {
        LocalDate dateBefore20 = DateTimeUtil.getGreaterThanEqualDate.apply(20);
        // tx1
        TreatmentProcedure treatmentProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("LessOrEqualThanConjunction")
                    .treatmentProcedure(treatmentProcedureDateBefore20)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore20);

        // tx2
        TreatmentProcedure treatment2ProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposal2DateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("LessOrEqualThanConjunction")
                    .treatmentProcedure(treatment2ProcedureDateBefore20)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposal2DateBefore20);

        NhiExtendTreatmentProcedure lessOrEqualThanConjunction = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("LessOrEqualThanConjunction")
            .check("");
        nhiService.checkInterval.accept(lessOrEqualThanConjunction);
        assertThat(lessOrEqualThanConjunction.getCheck()).isEqualTo(lessOrEqualThanConjunction.getA73() + " 30 天內不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalLessOrEqualThanWithDisjunction() {
        LocalDate dateBefore20 = DateTimeUtil.getGreaterThanEqualDate.apply(20);
        LocalDate dateBefore10 = DateTimeUtil.getGreaterThanEqualDate.apply(10);
        // tx1
        TreatmentProcedure treatmentProcedureDateBefore20 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore20 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("LessOrEqualThanDisjunction")
                    .treatmentProcedure(treatmentProcedureDateBefore20)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore20);

        // tx2
        TreatmentProcedure treatmentProcedureDateBefore10 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        NhiExtendDisposal nhiExtDisposalDateBefore10 = new NhiExtendDisposal()
            .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
            .a17(dateBefore10.getYear() - 1911 + monthFormatter.format(dateBefore10) + dayFormatter.format(dateBefore10))
            .patientId(1L)
            .nhiExtendTreatmentProcedures(Collections.singleton(
                new NhiExtendTreatmentProcedure()
                    .a73("LessOrEqualThanDisjunction")
                    .treatmentProcedure(treatmentProcedureDateBefore10)
                )
            );
        nhiExtendDisposalService.save(nhiExtDisposalDateBefore10);

        NhiExtendTreatmentProcedure lessOrEqualThanDisjunction = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(1L)
            )
            .a73("LessOrEqualThanDisjunction")
            .check("");
        nhiService.checkInterval.accept(lessOrEqualThanDisjunction);
        assertThat(lessOrEqualThanDisjunction.getCheck()).isEqualTo(lessOrEqualThanDisjunction.getA73() + " 30 天內不得重複申報 1 次\n");
    }

    @Test
    @Transactional
    public void testCheckIntervalLifetime() {
        Patient patient = patientService.save(PatientResourceIntTest.createEntity(em));
        Map<String, Object> lifetime = new HashMap<String, Object>() {{
            put("Lifetime", "11,12,13");
        }};
        nhiExtendPatientService.update(patient.getNhiExtendPatient().lifetime(lifetime));

        NhiExtendTreatmentProcedure lessOrEqualThanDisjunction = new NhiExtendTreatmentProcedure()
            .nhiExtendDisposal(new NhiExtendDisposal()
                .uploadStatus(NhiExtendDisposalUploadStatus.NONE)
                .patientId(patient.getId())
            )
            .a73("Lifetime")
            .a74("11")
            .check("");
        nhiService.checkInterval.accept(lessOrEqualThanDisjunction);
        assertThat(lessOrEqualThanDisjunction.getCheck()).isEqualTo(lessOrEqualThanDisjunction.getA73() + " 同顆牙不得重複申報 1 次\n");
    }
}

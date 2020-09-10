package io.dentall.totoro.service;

import io.dentall.totoro.business.dto.Rule;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

public class NhiServiceMockTest {

    private final ZoneOffset ZONE_OFF_SET = ZoneOffset.of("+08:00");

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");

    // 增加虛擬 Id，用以測試非自身 treatment procedure 的內容是否通過檢核
    private final Long fakeId1 = 1L;
    private final Long fakeId2 = 2L;
    private final Long fakeId3 = 3L;

    private final Function<Integer, LocalDate> getGreaterThanEqualDate = days ->
        OffsetDateTime.now(ZONE_OFF_SET).toLocalDate().minusDays(days);

    private final LocalDate today = LocalDate.now(ZONE_OFF_SET);

    @Mock
    private NhiExtendDisposalRepository mockNhiExtendDisposalRepository;

    @InjectMocks
    private NhiService nhiService;

    private Patient patient;

    private TreatmentTask treatmentTask;

    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

    private NhiExtendDisposal nhiExtDisposalToday;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        patient = new Patient();
        patient.setId(1L);
        Treatment treatment = new Treatment().patient(patient);
        TreatmentPlan treatmentPlan = new TreatmentPlan().treatment(treatment);
        treatmentTask = new TreatmentTask().treatmentPlan(treatmentPlan);

        // today
        Disposal disposal = new Disposal();
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure().treatmentTask(treatmentTask).disposal(disposal);
        nhiExtendTreatmentProcedure = new NhiExtendTreatmentProcedure()
            .treatmentProcedure(treatmentProcedure)
            .check("");
        nhiExtDisposalToday = new NhiExtendDisposal()
            .a17(today.getYear() - 1911 + monthFormatter.format(today) + dayFormatter.format(today))
            .nhiExtendTreatmentProcedures(Collections.singleton(nhiExtendTreatmentProcedure));
        disposal.setNhiExtendDisposals(Collections.singleton(nhiExtDisposalToday));
    }

    @Test
    public void testCheckIntervalLessOrEqualThan() {
        final String code = "LessOrEqualThan180";

        nhiExtendTreatmentProcedure.setA73(code);
        nhiExtendTreatmentProcedure.setId(fakeId1);

        Rule rule = new Rule();
        rule.setInterval("<=180");
        ReflectionTestUtils.setField(nhiService, "rules", new HashMap<String, Rule>() {{
            put(code, rule);
        }});

        // 100 days ago
        Disposal disposalDateBefore100 = new Disposal();
        TreatmentProcedure treatmentProcedureDateBefore100 = new TreatmentProcedure().treatmentTask(treatmentTask).disposal(disposalDateBefore100);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureDateBefore100 = new NhiExtendTreatmentProcedure()
            .a73(code)
            .treatmentProcedure(treatmentProcedureDateBefore100);
        nhiExtendTreatmentProcedureDateBefore100.setId(fakeId2);
        LocalDate dateBefore100 = getGreaterThanEqualDate.apply(100);
        NhiExtendDisposal nhiExtDisposalDateBefore100 = new NhiExtendDisposal()
            .a17(dateBefore100.getYear() - 1911 + monthFormatter.format(dateBefore100) + dayFormatter.format(dateBefore100))
            .nhiExtendTreatmentProcedures(Collections.singleton(nhiExtendTreatmentProcedureDateBefore100));
        disposalDateBefore100.setNhiExtendDisposals(Collections.singleton(nhiExtDisposalDateBefore100));

        // 20 days ago
        Disposal disposalDateBefore20 = new Disposal();
        TreatmentProcedure treatmentProcedureDateBefore20 = new TreatmentProcedure().treatmentTask(treatmentTask).disposal(disposalDateBefore20);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureDateBefore20 = new NhiExtendTreatmentProcedure()
            .a73(code)
            .treatmentProcedure(treatmentProcedureDateBefore20);
        nhiExtendTreatmentProcedureDateBefore20.setId(fakeId3);
        LocalDate dateBefore20 = getGreaterThanEqualDate.apply(20);
        NhiExtendDisposal nhiExtDisposalDateBefore20 = new NhiExtendDisposal()
            .a19("2")
            .a54(dateBefore20.getYear() - 1911 + monthFormatter.format(dateBefore20) + dayFormatter.format(dateBefore20))
            .nhiExtendTreatmentProcedures(Collections.singleton(nhiExtendTreatmentProcedureDateBefore20));
        disposalDateBefore20.setNhiExtendDisposals(Collections.singleton(nhiExtDisposalDateBefore20));

        Mockito
            .when(mockNhiExtendDisposalRepository.findByDateBetweenAndPatientId(today.minusDays(180), today, patient.getId()))
            .thenReturn(Arrays.asList(nhiExtDisposalDateBefore100, nhiExtDisposalDateBefore20, nhiExtDisposalToday));

        nhiService.checkInterval.accept(nhiExtendTreatmentProcedure);
        assertThat(nhiExtendTreatmentProcedure.getCheck()).isEqualTo("180 天內不得重複申報。上次：" + formatter.format(dateBefore20) + "(20 天前)\n");
    }

    @Test
    public void testCheckIntervalMonth() {
        final String code = "Month1";

        nhiExtendTreatmentProcedure.setId(fakeId1);
        nhiExtendTreatmentProcedure.setA73(code);

        Rule rule = new Rule();
        rule.setInterval("MMx1");
        ReflectionTestUtils.setField(nhiService, "rules", new HashMap<String, Rule>() {{
            put(code, rule);
        }});

        // 1st date of month
        Disposal disposalMonthFirstDate = new Disposal();
        TreatmentProcedure treatmentProcedureMonthFirstDate = new TreatmentProcedure().treatmentTask(treatmentTask).disposal(disposalMonthFirstDate);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureMonthFirstDate = new NhiExtendTreatmentProcedure()
            .a73(code)
            .treatmentProcedure(treatmentProcedureMonthFirstDate);
        nhiExtendTreatmentProcedureMonthFirstDate.setId(fakeId2);
        LocalDate monthFirstDate = today.with(TemporalAdjusters.firstDayOfMonth());
        NhiExtendDisposal nhiExtDisposalMonthFirstDate = new NhiExtendDisposal()
            .a17(monthFirstDate.getYear() - 1911 + monthFormatter.format(monthFirstDate) + dayFormatter.format(monthFirstDate))
            .nhiExtendTreatmentProcedures(Collections.singleton(nhiExtendTreatmentProcedureMonthFirstDate));
        disposalMonthFirstDate.setNhiExtendDisposals(Collections.singleton(nhiExtDisposalMonthFirstDate));

        Mockito
            .when(mockNhiExtendDisposalRepository.findByDateBetweenAndPatientId(monthFirstDate, today, patient.getId()))
            .thenReturn(Arrays.asList(nhiExtDisposalMonthFirstDate, nhiExtDisposalToday));

        nhiService.checkInterval.accept(nhiExtendTreatmentProcedure);
        assertThat(nhiExtendTreatmentProcedure.getCheck()).isEqualTo(
            "同一月份內不得重複申報。上次：" + formatter.format(monthFirstDate) + "(" + DAYS.between(monthFirstDate, today) + " 天前)\n"
        );
    }

    @Test
    public void testCheckIntervalMonthWithQuadrants() {
        final String code = "Month2Q1";

        nhiExtendTreatmentProcedure.setId(fakeId1);
        nhiExtendTreatmentProcedure.setA73(code);
        nhiExtendTreatmentProcedure.setA74("11");

        Rule rule = new Rule();
        rule.setInterval("MMx2;Qx1");
        ReflectionTestUtils.setField(nhiService, "rules", new HashMap<String, Rule>() {{
            put(code, rule);
        }});

        // 1st date of month: 1
        Disposal disposalMonthFirstDate1 = new Disposal();
        TreatmentProcedure treatmentProcedureMonthFirstDate1 = new TreatmentProcedure().treatmentTask(treatmentTask).disposal(disposalMonthFirstDate1);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureMonthFirstDate1 = new NhiExtendTreatmentProcedure()
            .a73(code)
            .a74("FM")
            .treatmentProcedure(treatmentProcedureMonthFirstDate1);
        nhiExtendTreatmentProcedureMonthFirstDate1.setId(fakeId2);
        LocalDate monthFirstDate1 = today.with(TemporalAdjusters.firstDayOfMonth());
        NhiExtendDisposal nhiExtDisposalMonthFirstDate1 = new NhiExtendDisposal()
            .a17(monthFirstDate1.getYear() - 1911 + monthFormatter.format(monthFirstDate1) + dayFormatter.format(monthFirstDate1))
            .nhiExtendTreatmentProcedures(Collections.singleton(nhiExtendTreatmentProcedureMonthFirstDate1));
        disposalMonthFirstDate1.setNhiExtendDisposals(Collections.singleton(nhiExtDisposalMonthFirstDate1));

        // 1st date of month: 2
        Disposal disposalMonthFirstDate2 = new Disposal();
        TreatmentProcedure treatmentProcedureMonthFirstDate2 = new TreatmentProcedure().treatmentTask(treatmentTask).disposal(disposalMonthFirstDate2);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureMonthFirstDate2 = new NhiExtendTreatmentProcedure()
            .a73(code)
            .a74("FM")
            .treatmentProcedure(treatmentProcedureMonthFirstDate2);
        nhiExtendTreatmentProcedureMonthFirstDate2.setId(fakeId3);
        LocalDate monthFirstDate = today.with(TemporalAdjusters.firstDayOfMonth());
        NhiExtendDisposal nhiExtDisposalMonthFirstDate2 = new NhiExtendDisposal()
            .a17(monthFirstDate.getYear() - 1911 + monthFormatter.format(monthFirstDate) + dayFormatter.format(monthFirstDate))
            .nhiExtendTreatmentProcedures(Collections.singleton(nhiExtendTreatmentProcedureMonthFirstDate2));
        disposalMonthFirstDate2.setNhiExtendDisposals(Collections.singleton(nhiExtDisposalMonthFirstDate2));

        Mockito
            .when(mockNhiExtendDisposalRepository.findByDateBetweenAndPatientId(monthFirstDate, today, patient.getId()))
            .thenReturn(Arrays.asList(nhiExtDisposalMonthFirstDate1, nhiExtDisposalMonthFirstDate2, nhiExtDisposalToday));

        nhiService.checkInterval.accept(nhiExtendTreatmentProcedure);
        assertThat(nhiExtendTreatmentProcedure.getCheck()).isEqualTo(
            "同一月份內不得申報超過 2 次。上次：" + formatter.format(monthFirstDate) + "(" + DAYS.between(monthFirstDate, today) + " 天前)\n" +
            "同象限不得重複申報 1 次。上次：" + formatter.format(monthFirstDate) + "(" + DAYS.between(monthFirstDate, today) + " 天前)\n"
        );
    }

    @Test
    public void testCheckIntervalSameHospital() {
        final String code = "SameHospital";
        final String hospital = "XYZ";

        nhiExtendTreatmentProcedure.setId(fakeId1);
        nhiExtendTreatmentProcedure.setA73(code);
        nhiExtendTreatmentProcedure.getTreatmentProcedure().getDisposal().getNhiExtendDisposals().iterator().next().a14(hospital);

        Rule rule = new Rule();
        rule.setInterval("<=730;SHx1");
        ReflectionTestUtils.setField(nhiService, "rules", new HashMap<String, Rule>() {{
            put(code, rule);
        }});

        // last year
        Disposal disposalLastYearDate = new Disposal();
        TreatmentProcedure treatmentProcedureMonthFirstDate = new TreatmentProcedure().treatmentTask(treatmentTask).disposal(disposalLastYearDate);
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedureMonthFirstDate = new NhiExtendTreatmentProcedure()
            .a73(code)
            .treatmentProcedure(treatmentProcedureMonthFirstDate);
        nhiExtendTreatmentProcedureMonthFirstDate.setId(fakeId2);
        LocalDate lastYearDate = today.minusYears(1);
        NhiExtendDisposal nhiExtDisposalLastYearDate = new NhiExtendDisposal()
            .a14(hospital)
            .a17(lastYearDate.getYear() - 1911 + monthFormatter.format(lastYearDate) + dayFormatter.format(lastYearDate))
            .nhiExtendTreatmentProcedures(Collections.singleton(nhiExtendTreatmentProcedureMonthFirstDate));
        disposalLastYearDate.setNhiExtendDisposals(Collections.singleton(nhiExtDisposalLastYearDate));

        Mockito
            .when(mockNhiExtendDisposalRepository.findByDateBetweenAndPatientId(getGreaterThanEqualDate.apply(730), today, patient.getId()))
            .thenReturn(Arrays.asList(nhiExtDisposalLastYearDate, nhiExtDisposalToday));

        nhiService.checkInterval.accept(nhiExtendTreatmentProcedure);
        assertThat(nhiExtendTreatmentProcedure.getCheck()).isEqualTo(
            "同一院所不得重複申報 1 次。上次：" + formatter.format(lastYearDate) + "(" + DAYS.between(lastYearDate, today) + " 天前)\n"
        );
    }
}

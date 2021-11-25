package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyNhiVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyNhiVo.Summary;
import io.dentall.totoro.business.service.report.treatment.MonthlyNhiReportSetting;
import io.dentall.totoro.repository.ReportDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class MonthlyNhiReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private MonthlyNhiReportBuilderService monthlyNhiReportBuilderService;

    private final AtomicLong disposalIdGenerator = new AtomicLong();

    private NhiVo newData(long doctorId, LocalDate disposalDate, long procedureId) {
        NhiVo vo = new NhiVo();
        vo.setDisposalId(disposalIdGenerator.incrementAndGet());
        vo.setDisposalDate(disposalDate);
        vo.setDoctorId(doctorId);
        vo.setDoctorName("D" + doctorId);
        vo.setPatientId(1L);
        vo.setPatientName("P1");
        vo.setProcedureId(procedureId);
        vo.setProcedurePoint(100 * procedureId);
        vo.setCardNumber("IC" + vo.getDisposalId());
        return vo;
    }

    private List<NhiVo> getData() {
        NhiVo vo1_1 = newData(1L, LocalDate.now(), 1L);
        NhiVo vo1_2 = newData(1L, LocalDate.now(), 2L);
        NhiVo vo1_3 = newData(1L, LocalDate.now().minusMonths(1), 1L);
        NhiVo vo1_4 = newData(1L, LocalDate.now().minusMonths(1), 2L);

        NhiVo vo2_1 = newData(2L, LocalDate.now(), 1L);
        NhiVo vo2_2 = newData(2L, LocalDate.now(), 2L);
        NhiVo vo2_3 = newData(2L, LocalDate.now().minusMonths(1), 1L);
        NhiVo vo2_4 = newData(2L, LocalDate.now().minusMonths(1), 2L);

        return asList(vo1_1, vo1_2, vo1_3, vo1_4, vo2_1, vo2_2, vo2_3, vo2_4);
    }


    @Test
    public void testMonthlyNhiReportData() {
        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(getData());

        ReportDataProvider<MonthlyNhiReportSetting, List<SubjectMonthlyNhiVo>> provider = monthlyNhiReportBuilderService.getDataProvider();
        MonthlyNhiReportSetting setting = new MonthlyNhiReportSetting();
        setting.setBeginMonth(YearMonth.now());
        setting.setEndMonth(YearMonth.now());
        List<SubjectMonthlyNhiVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(3); // clinic and two doctors
        SubjectMonthlyNhiVo clinicMonthly = resultList.stream().filter(result -> result.getSubjectId() == Long.MIN_VALUE).findFirst().get();
        SubjectMonthlyNhiVo doctor1Monthly = resultList.stream().filter(result -> result.getSubjectId() == 1L).findFirst().get();
        SubjectMonthlyNhiVo doctor2Monthly = resultList.stream().filter(result -> result.getSubjectId() == 2L).findFirst().get();

        assertThat(clinicMonthly.getSummaryList().size()).isEqualTo(4); // two months * two procedure
        assertThat(doctor1Monthly.getSummaryList().size()).isEqualTo(4); // two months * two procedure
        assertThat(doctor2Monthly.getSummaryList().size()).isEqualTo(4); // two months * two procedure

        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 2, 2, 200L);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 2, 2, 400L);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 2, 2, 200L);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 2, 2, 400L);

        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 100L);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 1, 1, 200L);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 100L);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 1, 1, 200L);

        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 100L);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 1, 1, 200L);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 100L);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 1, 1, 200L);
    }

    private void checkSummary(List<Summary> summaryList, YearMonth yearMonth, Long procedureId, int patientCount, int procedureCount, long procedurePoint) {
        List<Summary> clinicSummary1 = summaryList.stream()
            .filter(summary -> summary.getDisposalMonth().compareTo(yearMonth) == 0)
            .filter(summary -> summary.getProcedureId() == procedureId)
            .collect(toList());
        assertThat(clinicSummary1.size()).isEqualTo(1);
        assertThat(clinicSummary1.get(0).getPatientCount()).isEqualTo(patientCount);
        assertThat(clinicSummary1.get(0).getProcedureCount()).isEqualTo(procedureCount);
        assertThat(clinicSummary1.get(0).getProcedurePoint()).isEqualTo(procedurePoint);

    }

    @Test
    public void testMonthlyNhiReportDataFilterByDoctor() {
        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(getData());

        ReportDataProvider<MonthlyNhiReportSetting, List<SubjectMonthlyNhiVo>> provider = monthlyNhiReportBuilderService.getDataProvider();
        MonthlyNhiReportSetting setting = new MonthlyNhiReportSetting();
        setting.setBeginMonth(YearMonth.now());
        setting.setEndMonth(YearMonth.now());
        setting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        List<SubjectMonthlyNhiVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(1);

        SubjectMonthlyNhiVo doctorMonthly = resultList.get(0);
        assertThat(doctorMonthly.getSubjectId()).isEqualTo(1L);

        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 100L);
        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 1, 1, 200L);
        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 100L);
        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 1, 1, 200L);
    }

    @Test
    public void testMonthlyNhiReportDataFilterByNhiProcedure() {
        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(getData());

        ReportDataProvider<MonthlyNhiReportSetting, List<SubjectMonthlyNhiVo>> provider = monthlyNhiReportBuilderService.getDataProvider();
        MonthlyNhiReportSetting setting = new MonthlyNhiReportSetting();
        setting.setBeginMonth(YearMonth.now());
        setting.setEndMonth(YearMonth.now());
        setting.setIncludeNhiProcedureIds(new HashSet<>(asList(1L)));
        List<SubjectMonthlyNhiVo> resultList = provider.get(setting);

        List<Summary> otherProcedureList = resultList
            .stream()
            .map(SubjectMonthlyNhiVo::getSummaryList)
            .flatMap(Collection::stream)
            .filter(summary -> summary.getProcedureId() != 1L)
            .collect(toList());
        assertThat(otherProcedureList.size()).isEqualTo(0);

        SubjectMonthlyNhiVo clinicMonthly = resultList.stream().filter(result -> result.getSubjectId() == Long.MIN_VALUE).findFirst().get();
        SubjectMonthlyNhiVo doctor1Monthly = resultList.stream().filter(result -> result.getSubjectId() == 1L).findFirst().get();
        SubjectMonthlyNhiVo doctor2Monthly = resultList.stream().filter(result -> result.getSubjectId() == 2L).findFirst().get();

        assertThat(clinicMonthly.getSummaryList().size()).isEqualTo(2); // two months * one procedure
        assertThat(doctor1Monthly.getSummaryList().size()).isEqualTo(2); // two months * one procedure
        assertThat(doctor2Monthly.getSummaryList().size()).isEqualTo(2); // two months * one procedure

        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 2, 2, 200L);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 2, 2, 200L);

        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 100L);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 100L);

        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 100L);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 100L);
    }
}

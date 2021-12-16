package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.OwnExpenseVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyOwnExpenseVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyOwnExpenseVo.Summary;
import io.dentall.totoro.business.service.report.treatment.MonthlyOwnExpenseReportSetting;
import io.dentall.totoro.repository.ReportDataRepository;
import io.dentall.totoro.repository.ToothRepository;
import io.dentall.totoro.service.dto.table.ToothTable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class MonthlyOwnExpenseReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @MockBean
    private ToothRepository toothRepository;

    @Autowired
    private MonthlyOwnExpenseReportBuilderService monthlyOwnExpenseReportBuilderService;

    private final AtomicLong disposalIdGenerator = new AtomicLong();

    private OwnExpenseVo newData(long doctorId, LocalDate disposalDate, long procedureId) {
        OwnExpenseVo vo = new OwnExpenseVo();
        vo.setDisposalId(disposalIdGenerator.incrementAndGet());
        vo.setDisposalDate(disposalDate);
        vo.setDoctorId(doctorId);
        vo.setDoctorName("D" + doctorId);
        vo.setPatientId(1L);
        vo.setPatientName("P1");
        vo.setProcedureId(procedureId);
        vo.setTreatmentProcedureId(procedureId);
        vo.setCardNumber("IC" + vo.getDisposalId());
        return vo;
    }

    private List<OwnExpenseVo> getData() {
        OwnExpenseVo vo1_1 = newData(1L, LocalDate.now(), 1L);
        OwnExpenseVo vo1_2 = newData(1L, LocalDate.now(), 2L);
        OwnExpenseVo vo1_3 = newData(1L, LocalDate.now().minusMonths(1), 1L);
        OwnExpenseVo vo1_4 = newData(1L, LocalDate.now().minusMonths(1), 2L);

        OwnExpenseVo vo2_1 = newData(2L, LocalDate.now(), 1L);
        OwnExpenseVo vo2_2 = newData(2L, LocalDate.now(), 2L);
        OwnExpenseVo vo2_3 = newData(2L, LocalDate.now().minusMonths(1), 1L);
        OwnExpenseVo vo2_4 = newData(2L, LocalDate.now().minusMonths(1), 2L);

        return asList(vo1_1, vo1_2, vo1_3, vo1_4, vo2_1, vo2_2, vo2_3, vo2_4);
    }

    private Set<ToothTable> getToothData(int count) {
        Set<ToothTable> set = new HashSet<>();

        List<String> tmp = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            tmp.add("1" + i);
        }

        Tooth tooth = new Tooth();
        tooth.setPosition(String.join("", tmp));
        set.add(tooth);
        return set;
    }

    @Test
    public void testMonthlyOwnExpenseReportData() {
        when(reportDataRepository.findOwnExpenseVoBetween(any(), any())).thenReturn(getData());
        when(toothRepository.findToothByTreatmentProcedure_Id(1L)).thenReturn(getToothData(1));
        when(toothRepository.findToothByTreatmentProcedure_Id(2L)).thenReturn(getToothData(2));

        ReportDataProvider<MonthlyOwnExpenseReportSetting, List<SubjectMonthlyOwnExpenseVo>> provider = monthlyOwnExpenseReportBuilderService.getDataProvider();
        MonthlyOwnExpenseReportSetting setting = new MonthlyOwnExpenseReportSetting();
        setting.setBeginMonth(YearMonth.now());
        setting.setEndMonth(YearMonth.now());
        List<SubjectMonthlyOwnExpenseVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(3); // clinic and two doctors
        SubjectMonthlyOwnExpenseVo clinicMonthly = resultList.stream().filter(result -> result.getSubjectId() == Long.MIN_VALUE).findFirst().get();
        SubjectMonthlyOwnExpenseVo doctor1Monthly = resultList.stream().filter(result -> result.getSubjectId() == 1L).findFirst().get();
        SubjectMonthlyOwnExpenseVo doctor2Monthly = resultList.stream().filter(result -> result.getSubjectId() == 2L).findFirst().get();

        assertThat(clinicMonthly.getSummaryList().size()).isEqualTo(4); // two months * two procedure
        assertThat(doctor1Monthly.getSummaryList().size()).isEqualTo(4); // two months * two procedure
        assertThat(doctor2Monthly.getSummaryList().size()).isEqualTo(4); // two months * two procedure

        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 2, 2, 2);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 2, 2, 4);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 2, 2, 2);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 2, 2, 4);

        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 1);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 1, 1, 2);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 1);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 1, 1, 2);

        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 1);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 1, 1, 2);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 1);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 1, 1, 2);
    }

    private void checkSummary(List<Summary> summaryList, YearMonth yearMonth, Long procedureId, int patientCount, int procedureCount, int toothCount) {
        List<Summary> clinicSummary1 = summaryList.stream()
            .filter(summary -> summary.getDisposalMonth().compareTo(yearMonth) == 0)
            .filter(summary -> summary.getProcedureId() == procedureId)
            .collect(toList());
        assertThat(clinicSummary1.size()).isEqualTo(1);
        assertThat(clinicSummary1.get(0).getPatientCount()).isEqualTo(patientCount);
        assertThat(clinicSummary1.get(0).getProcedureCount()).isEqualTo(procedureCount);
        assertThat(clinicSummary1.get(0).getToothCount()).isEqualTo(toothCount);

    }

    @Test
    public void testMonthlyOwnExpenseReportDataFilterByDoctor() {
        when(reportDataRepository.findOwnExpenseVoBetween(any(), any())).thenReturn(getData());
        when(toothRepository.findToothByTreatmentProcedure_Id(1L)).thenReturn(getToothData(1));
        when(toothRepository.findToothByTreatmentProcedure_Id(2L)).thenReturn(getToothData(2));

        ReportDataProvider<MonthlyOwnExpenseReportSetting, List<SubjectMonthlyOwnExpenseVo>> provider = monthlyOwnExpenseReportBuilderService.getDataProvider();
        MonthlyOwnExpenseReportSetting setting = new MonthlyOwnExpenseReportSetting();
        setting.setBeginMonth(YearMonth.now());
        setting.setEndMonth(YearMonth.now());
        setting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        List<SubjectMonthlyOwnExpenseVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(1);

        SubjectMonthlyOwnExpenseVo doctorMonthly = resultList.get(0);
        assertThat(doctorMonthly.getSubjectId()).isEqualTo(1L);

        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 1);
        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 2L, 1, 1, 2);
        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 1);
        checkSummary(doctorMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 2L, 1, 1, 2);
    }

    @Test
    public void testMonthlyOwnExpenseReportDataFilterByNhiProcedure() {
        when(reportDataRepository.findOwnExpenseVoBetween(any(), any())).thenReturn(getData());
        when(toothRepository.findToothByTreatmentProcedure_Id(1L)).thenReturn(getToothData(1));
        when(toothRepository.findToothByTreatmentProcedure_Id(2L)).thenReturn(getToothData(2));

        ReportDataProvider<MonthlyOwnExpenseReportSetting, List<SubjectMonthlyOwnExpenseVo>> provider = monthlyOwnExpenseReportBuilderService.getDataProvider();
        MonthlyOwnExpenseReportSetting setting = new MonthlyOwnExpenseReportSetting();
        setting.setBeginMonth(YearMonth.now());
        setting.setEndMonth(YearMonth.now());
        setting.setIncludeOwnExpenseIds(new HashSet<>(asList(1L)));
        List<SubjectMonthlyOwnExpenseVo> resultList = provider.get(setting);

        List<Summary> otherProcedureList = resultList
            .stream()
            .map(SubjectMonthlyOwnExpenseVo::getSummaryList)
            .flatMap(Collection::stream)
            .filter(summary -> summary.getProcedureId() != 1L)
            .collect(toList());
        assertThat(otherProcedureList.size()).isEqualTo(0);

        SubjectMonthlyOwnExpenseVo clinicMonthly = resultList.stream().filter(result -> result.getSubjectId() == Long.MIN_VALUE).findFirst().get();
        SubjectMonthlyOwnExpenseVo doctor1Monthly = resultList.stream().filter(result -> result.getSubjectId() == 1L).findFirst().get();
        SubjectMonthlyOwnExpenseVo doctor2Monthly = resultList.stream().filter(result -> result.getSubjectId() == 2L).findFirst().get();

        assertThat(clinicMonthly.getSummaryList().size()).isEqualTo(2); // two months * one procedure
        assertThat(doctor1Monthly.getSummaryList().size()).isEqualTo(2); // two months * one procedure
        assertThat(doctor2Monthly.getSummaryList().size()).isEqualTo(2); // two months * one procedure

        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 2, 2, 2);
        checkSummary(clinicMonthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 2, 2, 2);

        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 1);
        checkSummary(doctor1Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 1);

        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now()), 1L, 1, 1, 1);
        checkSummary(doctor2Monthly.getSummaryList(), YearMonth.from(LocalDate.now().minusMonths(1)), 1L, 1, 1, 1);
    }

    private static class Tooth implements ToothTable {

        private String position;

        public void setPosition(String position) {
            this.position = position;
        }

        @Override
        public String getCreatedBy() {
            return null;
        }

        @Override
        public String getLastModifiedBy() {
            return null;
        }

        @Override
        public Instant getCreatedDate() {
            return null;
        }

        @Override
        public Instant getLastModifiedDate() {
            return null;
        }

        @Override
        public Long getId() {
            return null;
        }

        @Override
        public String getPosition() {
            return this.position;
        }

        @Override
        public String getSurface() {
            return null;
        }

        @Override
        public String getStatus() {
            return null;
        }

        @Override
        public Map<String, Object> getMetadata() {
            return null;
        }

        @Override
        public Long getTreatmentProcedure_Id() {
            return null;
        }
    }
}

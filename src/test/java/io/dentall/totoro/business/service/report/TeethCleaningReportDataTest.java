package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportMapper;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.TeethCleaningVo;
import io.dentall.totoro.business.service.report.followup.FollowupBookSetting;
import io.dentall.totoro.business.service.report.followup.TeethCleaningReportSetting;
import io.dentall.totoro.repository.ReportDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TeethCleaningReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private TeethCleaningReportBuilderService teethCleaningReportBuilderService;

    private final AtomicLong disposalIdGenerator = new AtomicLong();

    private NhiVo newData(long doctorId, LocalDate disposalDate, long patientId) {
        NhiVo vo = new NhiVo();
        vo.setDisposalId(disposalIdGenerator.incrementAndGet());
        vo.setDisposalDate(disposalDate);
        vo.setDoctorId(doctorId);
        vo.setDoctorName("D" + doctorId);
        vo.setPatientId(patientId);
        vo.setPatientName("P" + patientId);
        vo.setPatientBirth(LocalDate.now().minusYears(30));
        vo.setProcedureId(1L);
        vo.setProcedureCode("91004C");
        vo.setCardNumber("IC" + vo.getDisposalId());
        return vo;
    }

    private List<NhiVo> getData1(LocalDate endDate) {
        NhiVo vo1 = newData(1L, endDate.minusDays(179), 1L); // invalid
        NhiVo vo2 = newData(2L, endDate.minusDays(180), 2L); // invalid
        NhiVo vo3 = newData(3L, endDate.minusDays(181), 3L); // valid
        NhiVo vo4 = newData(4L, endDate.minusDays(182), 4L); // valid

        return asList(vo1, vo2, vo3, vo4);
    }

    private List<NhiVo> getAvailableDataList(List<NhiVo> dataList, LocalDate endDate) {
        return dataList.stream().filter(data -> {
            LocalDate availableDate = data.getDisposalDate().plusDays(181);
            return availableDate.isBefore(endDate) || availableDate.isEqual(endDate);
        }).collect(toList());
    }

    @Test
    public void testTeethCleaningReportData() {
        LocalDate endDate = LocalDate.now();
        List<NhiVo> dataList = getData1(endDate);
        List<NhiVo> availableDataList = getAvailableDataList(dataList, endDate);
        List<TeethCleaningVo> expectedDataList = availableDataList.stream().map(ReportMapper.INSTANCE::mapToTeethCleaningVo).collect(toList());

        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, List<Long>> groups = availableDataList
            .stream()
            .parallel()
            .map(NhiVo::getDisposalId)
            .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

        when(reportDataRepository.findAllPatientLatestNhiVo(singletonList("91004C"))).thenReturn(dataList);
        groups.values()
            .forEach(g -> {
                List<TeethCleaningVo> expected = expectedDataList.stream().filter(d -> g.contains(d.getDisposalId())).collect(toList());
                when(reportDataRepository.findTeethCleaningVoDisposalIds(g)).thenReturn(expected);
            });

        ReportDataProvider<TeethCleaningReportSetting, List<TeethCleaningVo>> provider = teethCleaningReportBuilderService.getDataProvider();

        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setEndDate(endDate);

        TeethCleaningReportSetting setting = new TeethCleaningReportSetting();
        setting.setBookSetting(bookSetting);

        List<TeethCleaningVo> resultList = provider.get(setting);
        assertThat(resultList.size()).isEqualTo(2);
        LocalDate nextAvailableTreatmentDate = resultList.get(0).getNextAvailableTreatmentDate();
        assertThat(nextAvailableTreatmentDate.isEqual(endDate) || nextAvailableTreatmentDate.isBefore(endDate)).isTrue();
        nextAvailableTreatmentDate = resultList.get(1).getNextAvailableTreatmentDate();
        assertThat(nextAvailableTreatmentDate.isEqual(endDate) || nextAvailableTreatmentDate.isBefore(endDate)).isTrue();
    }

    @Test
    public void testTeethCleaningReportDataFilterByDoctor() {
        LocalDate endDate = LocalDate.now();
        List<NhiVo> dataList = getData1(endDate);
        List<NhiVo> availableDataList = getAvailableDataList(dataList, endDate);
        List<TeethCleaningVo> expectedDataList = availableDataList.stream().map(ReportMapper.INSTANCE::mapToTeethCleaningVo).collect(toList());

        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, List<Long>> groups = availableDataList
            .stream()
            .parallel()
            .map(NhiVo::getDisposalId)
            .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

        when(reportDataRepository.findAllPatientLatestNhiVo(singletonList("91004C"))).thenReturn(dataList);
        groups.values()
            .forEach(g -> {
                List<TeethCleaningVo> expected = expectedDataList.stream().filter(d -> g.contains(d.getDisposalId())).collect(toList());
                when(reportDataRepository.findTeethCleaningVoDisposalIds(g)).thenReturn(expected);
            });

        ReportDataProvider<TeethCleaningReportSetting, List<TeethCleaningVo>> provider = teethCleaningReportBuilderService.getDataProvider();

        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setIncludeDoctorIds(new HashSet<>(Arrays.asList(3L)));
        bookSetting.setEndDate(endDate);

        TeethCleaningReportSetting setting = new TeethCleaningReportSetting();
        setting.setBookSetting(bookSetting);

        List<TeethCleaningVo> resultList = provider.get(setting);
        assertThat(resultList.size()).isEqualTo(1);
        LocalDate nextAvailableTreatmentDate = resultList.get(0).getNextAvailableTreatmentDate();
        assertThat(nextAvailableTreatmentDate.isEqual(endDate) || nextAvailableTreatmentDate.isBefore(endDate)).isTrue();
    }

}

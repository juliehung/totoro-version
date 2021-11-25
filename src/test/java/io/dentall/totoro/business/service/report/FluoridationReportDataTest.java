package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportMapper;
import io.dentall.totoro.business.service.report.dto.FluoridationVo;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.followup.FluoridationReportSetting;
import io.dentall.totoro.business.service.report.followup.FollowupBookSetting;
import io.dentall.totoro.repository.ReportDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static io.dentall.totoro.business.service.report.context.ReportHelper.toAge;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class FluoridationReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private FluoridationReportBuilderService dailyNhiReportBuilderService;

    private final AtomicLong disposalIdGenerator = new AtomicLong();

    private NhiVo newData(long doctorId, LocalDate disposalDate, long patientId, LocalDate birth) {
        NhiVo vo = new NhiVo();
        vo.setDisposalId(disposalIdGenerator.incrementAndGet());
        vo.setDisposalDate(disposalDate);
        vo.setDoctorId(doctorId);
        vo.setDoctorName("D" + doctorId);
        vo.setPatientId(patientId);
        vo.setPatientName("P" + patientId);
        vo.setPatientBirth(birth);
        vo.setPatientAge(toAge(birth, disposalDate));
        vo.setProcedureId(1L);
        vo.setProcedureCode("81");
        vo.setCardNumber("IC" + vo.getDisposalId());
        return vo;
    }

    private List<NhiVo> getData1(LocalDate endDate) {
        // 年齡是從就醫年月算起，所以生日要用就醫年月往前扣
        NhiVo vo1 = newData(1L, endDate.minusMonths(5), 1L, endDate.minusMonths(5).minusYears(5).minusMonths(11)); // invalid
        NhiVo vo2 = newData(2L, endDate.minusMonths(6), 2L, endDate.minusMonths(6).minusYears(5).minusMonths(11)); // valid
        NhiVo vo3 = newData(3L, endDate.minusMonths(6), 3L, endDate.minusMonths(6).minusYears(6)); // invalid
        NhiVo vo4 = newData(4L, endDate.minusMonths(7), 4L, endDate.minusMonths(7).minusYears(5).minusMonths(11)); // valid

        return asList(vo1, vo2, vo3, vo4);
    }

    private List<NhiVo> getAvailableDataList(List<NhiVo> dataList, LocalDate endDate) {
        return dataList.stream().filter(data -> {
            LocalDate availableDate = data.getDisposalDate().plusMonths(6);
            Period age = data.getPatientAge();
            return (availableDate.isBefore(endDate) || availableDate.isEqual(endDate)) && age.getYears() < 6;
        }).collect(toList());
    }

    @Test
    public void testFluoridationReportData() {
        LocalDate endDate = LocalDate.now();
        List<NhiVo> dataList = getData1(endDate);
        List<NhiVo> availableDataList = getAvailableDataList(dataList, endDate);
        List<FluoridationVo> expectedDataList = availableDataList.stream().map(ReportMapper.INSTANCE::mapToFluoridationVo).collect(toList());

        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, List<Long>> groups = availableDataList
            .stream()
            .parallel()
            .map(NhiVo::getDisposalId)
            .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

        when(reportDataRepository.findAllPatientLatestNhiVo(singletonList("81"))).thenReturn(dataList);
        groups.values()
            .forEach(g -> {
                List<FluoridationVo> expected = expectedDataList.stream().filter(d -> g.contains(d.getDisposalId())).collect(toList());
                when(reportDataRepository.findFluoridationVoDisposalIds(g)).thenReturn(expected);
            });

        ReportDataProvider<FluoridationReportSetting, List<FluoridationVo>> provider = dailyNhiReportBuilderService.getDataProvider();

        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setEndDate(endDate);

        FluoridationReportSetting setting = new FluoridationReportSetting();
        setting.setBookSetting(bookSetting);

        List<FluoridationVo> resultList = provider.get(setting);
        assertThat(resultList.size()).isEqualTo(2);
        LocalDate nextAvailableTreatmentDate = resultList.get(0).getNextAvailableTreatmentDate();
        assertThat(nextAvailableTreatmentDate.isEqual(endDate) || nextAvailableTreatmentDate.isBefore(endDate)).isTrue();
        nextAvailableTreatmentDate = resultList.get(1).getNextAvailableTreatmentDate();
        assertThat(nextAvailableTreatmentDate.isEqual(endDate) || nextAvailableTreatmentDate.isBefore(endDate)).isTrue();
    }

    @Test
    public void testFluoridationReportDataFilterByDoctor() {
        LocalDate endDate = LocalDate.now();
        List<NhiVo> dataList = getData1(endDate);
        List<NhiVo> availableDataList = getAvailableDataList(dataList, endDate);
        List<FluoridationVo> expectedDataList = availableDataList.stream().map(ReportMapper.INSTANCE::mapToFluoridationVo).collect(toList());

        AtomicInteger count = new AtomicInteger(1);
        Map<Integer, List<Long>> groups = availableDataList
            .stream()
            .parallel()
            .map(NhiVo::getDisposalId)
            .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

        when(reportDataRepository.findAllPatientLatestNhiVo(singletonList("81"))).thenReturn(dataList);
        groups.values()
            .forEach(g -> {
                List<FluoridationVo> expected = expectedDataList.stream().filter(d -> g.contains(d.getDisposalId())).collect(toList());
                when(reportDataRepository.findFluoridationVoDisposalIds(g)).thenReturn(expected);
            });

        ReportDataProvider<FluoridationReportSetting, List<FluoridationVo>> provider = dailyNhiReportBuilderService.getDataProvider();

        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setIncludeDoctorIds(new HashSet<>(Arrays.asList(2L)));
        bookSetting.setEndDate(endDate);

        FluoridationReportSetting setting = new FluoridationReportSetting();
        setting.setBookSetting(bookSetting);

        List<FluoridationVo> resultList = provider.get(setting);
        assertThat(resultList.size()).isEqualTo(1);
        LocalDate nextAvailableTreatmentDate = resultList.get(0).getNextAvailableTreatmentDate();
        assertThat(nextAvailableTreatmentDate.isEqual(endDate) || nextAvailableTreatmentDate.isBefore(endDate)).isTrue();
    }

}

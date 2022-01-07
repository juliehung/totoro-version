package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.PeriodontalVo;
import io.dentall.totoro.business.service.report.followup.FollowupBookSetting;
import io.dentall.totoro.business.service.report.followup.PeriodontalReportSetting;
import io.dentall.totoro.repository.ReportDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class PeriodontalReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private PeriodontalReportBuilderService periodontalReportBuilderService;

    private final AtomicLong disposalIdGenerator = new AtomicLong();

    private final List<String> codes = asList("91021C", "91022C", "91023C");

    private NhiVo newData(LocalDate disposalDate, long doctorId, long patientId, String code) {
        NhiVo vo = new NhiVo();
        vo.setDisposalId(disposalIdGenerator.incrementAndGet());
        vo.setDisposalDate(disposalDate);
        vo.setDoctorId(doctorId);
        vo.setPatientId(patientId);
        vo.setPatientBirth(LocalDate.now().minusYears(30));
        vo.setProcedureCode(code);
        return vo;
    }

    private List<NhiVo> getDataList() {
        NhiVo voA_1 = newData(LocalDate.of(2021, 1, 18), 1L, 1L, "91021C");
        NhiVo voA_2 = newData(LocalDate.of(2021, 2, 24), 1L, 1L, "91022C");
        NhiVo voB_1 = newData(LocalDate.of(2020, 10, 30), 2L, 2L, "91021C");
        NhiVo voB_2 = newData(LocalDate.of(2021, 2, 18), 2L, 2L, "91022C");
        NhiVo voB_3 = newData(LocalDate.of(2021, 3, 18), 2L, 2L, "91023C");
        return asList(voA_1, voA_2, voB_1, voB_2, voB_3);
    }

    @Test
    public void testPeriodontalReportData() {
        LocalDate beginDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 2, 28);
        List<NhiVo> dataList = getDataList();
        List<NhiVo> availableDateList = dataList.stream()
            .filter(d -> d.getDisposalDate().isAfter(beginDate) && d.getDisposalDate().isBefore(endDate))
            .collect(toList());
        AtomicInteger count = new AtomicInteger(1);

        Map<Integer, List<Long>> groups = dataList
            .stream()
            .parallel()
            .map(NhiVo::getPatientId)
            .distinct()
            .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

        when(reportDataRepository.findNhiVoBetweenAndCode(any(), any(), any())).thenReturn(availableDateList);
        groups.values()
            .forEach(g -> {
                List<NhiVo> expected = dataList.stream().filter(d -> g.contains(d.getPatientId())).collect(toList());
                when(reportDataRepository.findNhiVoPatientAndCode(g, codes)).thenReturn(expected);
            });

        ReportDataProvider<PeriodontalReportSetting, List<PeriodontalVo>> provider = periodontalReportBuilderService.getDataProvider();
        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setBeginDate(beginDate);
        bookSetting.setEndDate(endDate);
        PeriodontalReportSetting setting = new PeriodontalReportSetting();
        setting.setBookSetting(bookSetting);
        List<PeriodontalVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(2);

        Optional<PeriodontalVo> patientAOptional = resultList.stream().filter(vo -> vo.getPatientId() == 1L).findFirst();
        assertThat(patientAOptional.isPresent()).isTrue();

        PeriodontalVo patientA = patientAOptional.get();
        assertThat(patientA.getP4001Date()).isNotNull();
        assertThat(patientA.getP4002Date()).isNotNull();
        assertThat(patientA.getP4003Date()).isNull();

        Optional<PeriodontalVo> patientBOptional = resultList.stream().filter(vo -> vo.getPatientId() == 2L).findFirst();
        assertThat(patientBOptional.isPresent()).isTrue();

        PeriodontalVo patientB = patientBOptional.get();
        assertThat(patientB.getP4001Date()).isNotNull();
        assertThat(patientB.getP4002Date()).isNotNull();
        assertThat(patientB.getP4003Date()).isNotNull();
    }

    @Test
    public void testPeriodontalReportDataFilterByDocotorId() {
        LocalDate beginDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 2, 28);
        List<NhiVo> dataList = getDataList();
        List<NhiVo> availableDateList = dataList.stream()
            .filter(d -> d.getDisposalDate().isAfter(beginDate) && d.getDisposalDate().isBefore(endDate))
            .collect(toList());
        AtomicInteger count = new AtomicInteger(1);

        Map<Integer, List<Long>> groups = dataList
            .stream()
            .parallel()
            .map(NhiVo::getPatientId)
            .distinct()
            .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

        when(reportDataRepository.findNhiVoBetweenAndCode(any(), any(), any())).thenReturn(availableDateList);
        groups.values()
            .forEach(g -> {
                List<NhiVo> expected = dataList.stream().filter(d -> g.contains(d.getPatientId())).collect(toList());
                when(reportDataRepository.findNhiVoPatientAndCode(g, codes)).thenReturn(expected);
            });

        ReportDataProvider<PeriodontalReportSetting, List<PeriodontalVo>> provider = periodontalReportBuilderService.getDataProvider();
        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setBeginDate(beginDate);
        bookSetting.setEndDate(endDate);
        bookSetting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        PeriodontalReportSetting setting = new PeriodontalReportSetting();
        setting.setBookSetting(bookSetting);
        List<PeriodontalVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(1);

        Optional<PeriodontalVo> patientAOptional = resultList.stream().filter(vo -> vo.getPatientId() == 1L).findFirst();
        assertThat(patientAOptional.isPresent()).isTrue();

        PeriodontalVo patientA = patientAOptional.get();
        assertThat(patientA.getP4001Date()).isNotNull();
        assertThat(patientA.getP4002Date()).isNotNull();
        assertThat(patientA.getP4003Date()).isNull();

        Optional<PeriodontalVo> patientBOptional = resultList.stream().filter(vo -> vo.getPatientId() == 2L).findFirst();
        assertThat(patientBOptional.isPresent()).isFalse();
    }

}

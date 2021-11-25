package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.OwnExpenseVo;
import io.dentall.totoro.business.service.report.treatment.DailyOwnExpenseReportSetting;
import io.dentall.totoro.repository.ReportDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class DailyOwnExpenseReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private DailyOwnExpenseReportBuilderService dailyOwnExpenseReportBuilderService;

    @Test
    public void testDailyOwnExpenseReportData() {
        OwnExpenseVo vo = new OwnExpenseVo();
        List<OwnExpenseVo> dataList = asList(vo);

        when(reportDataRepository.findOwnExpenseVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyOwnExpenseReportSetting, List<OwnExpenseVo>> provider = dailyOwnExpenseReportBuilderService.getDataProvider();
        DailyOwnExpenseReportSetting setting = new DailyOwnExpenseReportSetting();
        List<OwnExpenseVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(dataList.size());
    }

    @Test
    public void testDailyOwnExpenseReportDataFilterByDoctor() {
        OwnExpenseVo vo1 = new OwnExpenseVo();
        vo1.setDoctorId(1L);
        OwnExpenseVo vo2 = new OwnExpenseVo();
        vo2.setDoctorId(2L);
        List<OwnExpenseVo> dataList = asList(vo1, vo2);

        when(reportDataRepository.findOwnExpenseVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyOwnExpenseReportSetting, List<OwnExpenseVo>> provider = dailyOwnExpenseReportBuilderService.getDataProvider();
        DailyOwnExpenseReportSetting setting = new DailyOwnExpenseReportSetting();
        setting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        List<OwnExpenseVo> resultList = provider.get(setting);
        OwnExpenseVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(1);
        assertThat(vo.getDoctorId()).isEqualTo(1L);
    }

    @Test
    public void testDailyOwnExpenseReportDataFilterByNhiProcedure() {
        OwnExpenseVo vo1 = new OwnExpenseVo();
        vo1.setProcedureId(1L);
        OwnExpenseVo vo2 = new OwnExpenseVo();
        vo2.setProcedureId(2L);
        List<OwnExpenseVo> dataList = asList(vo1, vo2);

        when(reportDataRepository.findOwnExpenseVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyOwnExpenseReportSetting, List<OwnExpenseVo>> provider = dailyOwnExpenseReportBuilderService.getDataProvider();
        DailyOwnExpenseReportSetting setting = new DailyOwnExpenseReportSetting();
        setting.setIncludeOwnExpenseIds(new HashSet<>(asList(1L)));
        List<OwnExpenseVo> resultList = provider.get(setting);
        OwnExpenseVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(1);
        assertThat(vo.getProcedureId()).isEqualTo(1L);
    }
}

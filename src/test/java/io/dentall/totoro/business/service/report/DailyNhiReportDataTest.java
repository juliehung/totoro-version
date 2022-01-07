package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.treatment.DailyNhiReportSetting;
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
public class DailyNhiReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private DailyNhiReportBuilderService dailyNhiReportBuilderService;

    @Test
    public void testDailyNhiReportData() {
        NhiVo vo = new NhiVo();
        List<NhiVo> dataList = asList(vo);

        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyNhiReportSetting, List<NhiVo>> provider = dailyNhiReportBuilderService.getDataProvider();
        DailyNhiReportSetting setting = new DailyNhiReportSetting();
        List<NhiVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(dataList.size());
    }

    @Test
    public void testDailyNhiReportDataFilterByDoctor() {
        NhiVo vo1 = new NhiVo();
        vo1.setDoctorId(1L);
        NhiVo vo2 = new NhiVo();
        vo2.setDoctorId(2L);
        List<NhiVo> dataList = asList(vo1, vo2);

        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyNhiReportSetting, List<NhiVo>> provider = dailyNhiReportBuilderService.getDataProvider();
        DailyNhiReportSetting setting = new DailyNhiReportSetting();
        setting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        List<NhiVo> resultList = provider.get(setting);
        NhiVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(1);
        assertThat(vo.getDoctorId()).isEqualTo(1L);
    }

    @Test
    public void testDailyNhiReportDataFilterByNhiProcedure() {
        NhiVo vo1 = new NhiVo();
        vo1.setProcedureId(1L);
        NhiVo vo2 = new NhiVo();
        vo2.setProcedureId(2L);
        List<NhiVo> dataList = asList(vo1, vo2);

        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyNhiReportSetting, List<NhiVo>> provider = dailyNhiReportBuilderService.getDataProvider();
        DailyNhiReportSetting setting = new DailyNhiReportSetting();
        setting.setIncludeNhiProcedureIds(new HashSet<>(asList(1L)));
        List<NhiVo> resultList = provider.get(setting);
        NhiVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(1);
        assertThat(vo.getProcedureId()).isEqualTo(1L);
    }
}

package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.DrugVo;
import io.dentall.totoro.business.service.report.treatment.DailyDrugReportSetting;
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
public class DailyDrugReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private DailyDrugReportBuilderService dailyDrugReportBuilderService;

    @Test
    public void testDailyDrugReportData() {
        DrugVo vo = new DrugVo();
        List<DrugVo> dataList = asList(vo);

        when(reportDataRepository.findDrugVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyDrugReportSetting, List<DrugVo>> provider = dailyDrugReportBuilderService.getDataProvider();
        DailyDrugReportSetting setting = new DailyDrugReportSetting();
        List<DrugVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(dataList.size());
    }

    @Test
    public void testDailyDrugReportDataFilterByDoctor() {
        DrugVo vo1 = new DrugVo();
        vo1.setDoctorId(1L);
        DrugVo vo2 = new DrugVo();
        vo2.setDoctorId(2L);
        List<DrugVo> dataList = asList(vo1, vo2);

        when(reportDataRepository.findDrugVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyDrugReportSetting, List<DrugVo>> provider = dailyDrugReportBuilderService.getDataProvider();
        DailyDrugReportSetting setting = new DailyDrugReportSetting();
        setting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        List<DrugVo> resultList = provider.get(setting);
        DrugVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(1);
        assertThat(vo.getDoctorId()).isEqualTo(1L);
    }

    @Test
    public void testDailyDrugReportDataFilterByNhiProcedure() {
        DrugVo vo1 = new DrugVo();
        vo1.setDrugId(1L);
        DrugVo vo2 = new DrugVo();
        vo2.setDrugId(2L);
        List<DrugVo> dataList = asList(vo1, vo2);

        when(reportDataRepository.findDrugVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<DailyDrugReportSetting, List<DrugVo>> provider = dailyDrugReportBuilderService.getDataProvider();
        DailyDrugReportSetting setting = new DailyDrugReportSetting();
        setting.setIncludeDrugIds(new HashSet<>(asList(1L)));
        List<DrugVo> resultList = provider.get(setting);
        DrugVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(1);
        assertThat(vo.getDrugId()).isEqualTo(1L);
    }
}

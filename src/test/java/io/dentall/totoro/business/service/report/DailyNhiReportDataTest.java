package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.treatment.DailyNhiReportSetting;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.repository.NhiProcedureRepository;
import io.dentall.totoro.repository.ReportDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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

    @MockBean
    private NhiProcedureRepository nhiProcedureRepository;

    private final AtomicLong disposalIdGenerator = new AtomicLong();

    private static Map<Long, String> codeMappings = new HashMap<>();

    @Before
    public void initCodeMappings() {
        codeMappings.put(1L, "0001");
        codeMappings.put(2L, "0002");
    }

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
        NhiVo vo1 = newData(1L, LocalDate.now(), 1L);
        NhiVo vo2 = newData(1L, LocalDate.now(), 2L);

        List<NhiVo> dataList = asList(vo1, vo2);

        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(dataList);

        NhiProcedure nhiProcedure = new NhiProcedure();
        nhiProcedure.setId(1L);
        nhiProcedure.setCode(codeMappings.get(1L));
        when(nhiProcedureRepository.findAllByIdIn(any(ArrayList.class))).thenReturn(asList(nhiProcedure));

        ReportDataProvider<DailyNhiReportSetting, List<NhiVo>> provider = dailyNhiReportBuilderService.getDataProvider();
        DailyNhiReportSetting setting = new DailyNhiReportSetting();
        setting.setIncludeNhiProcedureIds(new HashSet<>(asList(1L)));
        List<NhiVo> resultList = provider.get(setting);
        NhiVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(1);
        assertThat(vo.getProcedureId()).isEqualTo(1L);
    }

    private NhiVo newData(long doctorId, LocalDate disposalDate, long procedureId) {
        NhiVo vo = new NhiVo();
        vo.setDisposalId(disposalIdGenerator.incrementAndGet());
        vo.setDisposalDate(disposalDate);
        vo.setDoctorId(doctorId);
        vo.setDoctorName("D" + doctorId);
        vo.setPatientId(1L);
        vo.setPatientName("P1");
        vo.setProcedureId(procedureId);
        vo.setProcedureCode(codeMappings.get(procedureId));
        vo.setProcedurePoint(100 * procedureId);
        vo.setCardNumber("IC" + vo.getDisposalId());
        return vo;
    }
}

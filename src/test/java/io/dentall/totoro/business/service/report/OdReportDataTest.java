package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.followup.FollowupBookSetting;
import io.dentall.totoro.business.service.report.followup.OdReportSetting;
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
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class OdReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private OdReportBuilderService odReportBuilderService;

    public static final Set<String> CodesByOd = Stream.of(
        "89001C", "89002C", "89003C", "89004C", "89005C", "89008C", "89009C", "89010C", "89011C", "89012C"
    ).collect(toSet());

    @Test
    public void testOdReportData() {
        List<NhiVo> dataList = CodesByOd.stream().map(code -> {
            NhiVo vo = new NhiVo();
            vo.setDisposalDate(LocalDate.now());
            vo.setProcedureCode(code);
            return vo;
        }).collect(toList());
        NhiVo vo = new NhiVo();
        vo.setProcedureCode("90001C");
        dataList.add(vo);

        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<OdReportSetting, List<NhiVo>> provider = odReportBuilderService.getDataProvider();
        FollowupBookSetting bookSetting = new FollowupBookSetting();
        OdReportSetting setting = new OdReportSetting();
        setting.setBookSetting(bookSetting);
        List<NhiVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(dataList.size() - 1);
        long nonOdCodeCount = resultList.stream().filter(result -> !CodesByOd.contains(result.getProcedureCode())).count();
        assertThat(nonOdCodeCount).isEqualTo(0L);
    }

    @Test
    public void testOdReportDataFilterByDoctor1() {
        List<NhiVo> dataList = CodesByOd.stream().map(code -> {
            NhiVo vo = new NhiVo();
            vo.setDisposalDate(LocalDate.now());
            vo.setDoctorId(1L);
            vo.setProcedureCode(code);
            return vo;
        }).collect(toList());

        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<OdReportSetting, List<NhiVo>> provider = odReportBuilderService.getDataProvider();

        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        OdReportSetting setting = new OdReportSetting();
        setting.setBookSetting(bookSetting);

        List<NhiVo> resultList = provider.get(setting);
        NhiVo vo = resultList.stream().findFirst().get();

        assertThat(resultList.size()).isEqualTo(dataList.size());
    }

    @Test
    public void testOdReportDataFilterByDoctor2() {
        List<NhiVo> dataList = CodesByOd.stream().map(code -> {
            NhiVo vo = new NhiVo();
            vo.setDisposalDate(LocalDate.now());
            vo.setDoctorId(2L);
            vo.setProcedureCode(code);
            return vo;
        }).collect(toList());

        when(reportDataRepository.findNhiVoBetween(any(), any())).thenReturn(dataList);

        ReportDataProvider<OdReportSetting, List<NhiVo>> provider = odReportBuilderService.getDataProvider();

        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        OdReportSetting setting = new OdReportSetting();
        setting.setBookSetting(bookSetting);

        List<NhiVo> resultList = provider.get(setting);
        assertThat(resultList.size()).isEqualTo(0);
    }

}

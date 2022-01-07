package io.dentall.totoro.business.service.report;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportMapper;
import io.dentall.totoro.business.service.report.dto.*;
import io.dentall.totoro.business.service.report.followup.EndoReportSetting;
import io.dentall.totoro.business.service.report.followup.FollowupBookSetting;
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
import java.util.concurrent.atomic.AtomicLong;

import static io.dentall.totoro.business.service.report.ReportTestHelper.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class EndoReportDataTest {

    @MockBean
    private ReportDataRepository reportDataRepository;

    @Autowired
    private EndoReportBuilderService endoReportBuilderService;

    private final AtomicLong disposalIdGenerator = new AtomicLong();

    private final List<String> codes = asList("90001C", "90002C", "90003C", "90015C", "90016C", "90018C", "90019C", "90020C");

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
        // A 有在選定區間內進行根管，而且是多次根管
        NhiVo voA_1 = newData(LocalDate.of(2021, 1, 18), 1L, 1L, "90001C");
        NhiVo voA_2 = newData(LocalDate.of(2021, 2, 24), 1L, 1L, "90001C");
        // B 有在選定區間內進行根管
        NhiVo voB_1 = newData(LocalDate.of(2021, 1, 30), 2L, 2L, "90001C");
        // C 有在選定區間內進行根管，但三個月內已有回診
        NhiVo voC_1 = newData(LocalDate.of(2021, 1, 18), 3L, 3L, "90001C");
        NhiVo voC_2 = newData(LocalDate.of(2021, 4, 18), 3L, 3L, "90001C");

        return asList(voA_1, voA_2, voB_1, voC_1, voC_2);
    }

    @Test
    public void testEndoReportData() {
        int gapMonths = 3;
        LocalDate beginDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 2, 28);
        LocalDate dateAfter3monthsOfEndDate = endDate.plusMonths(gapMonths);
        List<NhiVo> dataList = getDataList();
        List<DisposalVo> disposalDataList = dataList.stream().map(ReportMapper.INSTANCE::mapToDisposalVo).collect(toList());
        List<NhiVo> candidateList = dataList.stream().filter(d -> d.getDisposalDate().isAfter(beginDate) && d.getDisposalDate().isBefore(endDate)).collect(toList());
        List<Long> patientIds = candidateList.stream().map(NhiVo::getPatientId).distinct().collect(toList());

        // subsequent
        List<Long> subsequentPatientIds = asList(1L, 2L);
        List<NhiVo> availableList = candidateList.stream()
            .filter(d -> subsequentPatientIds.contains(d.getPatientId()))
            .filter(d -> d.getDisposalDate().isEqual(LocalDate.of(2021, 2, 24)) ||
                d.getDisposalDate().isEqual(LocalDate.of(2021, 1, 30)))
            .collect(toList());
        LocalDate firstAvailableSubsequentDate = beginDate.plusMonths(gapMonths).plusDays(1L);
        List<NhiVo> nhiSubsequentList = getNhiSubsequentList(availableList, gapMonths);
        List<OwnExpenseVo> ownExpenseSubsequentList = getOwnExpenseSubsequentList(availableList, gapMonths);
        List<DrugVo> drugSubsequentList = getDrugSubsequentList(availableList, gapMonths);

        assertThat(candidateList.size()).isEqualTo(4);
        assertThat(patientIds.size()).isEqualTo(3);
        assertThat(patientIds.contains(1L)).isTrue();
        assertThat(patientIds.contains(2L)).isTrue();
        assertThat(patientIds.contains(3L)).isTrue();

        when(reportDataRepository.findNhiVoBetweenAndCode(beginDate, endDate, codes)).thenReturn(candidateList);
        when(reportDataRepository.findDisposalVoBetweenAndPatient(beginDate, dateAfter3monthsOfEndDate, patientIds)).thenReturn(disposalDataList);
        when(reportDataRepository.findNhiVoAfterAndPatient(firstAvailableSubsequentDate, subsequentPatientIds)).thenReturn(nhiSubsequentList);
        when(reportDataRepository.findOwnExpenseVoAfterAndPatient(firstAvailableSubsequentDate, subsequentPatientIds)).thenReturn(ownExpenseSubsequentList);
        when(reportDataRepository.findDrugVoAfterAndPatient(firstAvailableSubsequentDate, subsequentPatientIds)).thenReturn(drugSubsequentList);

        ReportDataProvider<EndoReportSetting, List<EndoVo>> provider = endoReportBuilderService.getDataProvider();
        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setBeginDate(beginDate);
        bookSetting.setEndDate(endDate);
        EndoReportSetting setting = new EndoReportSetting();
        setting.setBookSetting(bookSetting);
        setting.setFollowupGapMonths(gapMonths);
        List<EndoVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(2);

        List<EndoVo> patientAList = resultList.stream().filter(r -> r.getPatientId() == 1L).collect(toList());
        assertThat(patientAList.size()).isEqualTo(1);
        EndoVo patientA = patientAList.get(0);
        assertThat(patientA.getDisposalDate()).isEqualTo(LocalDate.of(2021, 2, 24));
        assertThat(patientA.getSubsequentNhiList().size()).isEqualTo(1);
        assertThat(patientA.getSubsequentNhiList().get(0).getDisposalDate().isAfter(patientA.getDisposalDate())).isTrue();
        assertThat(patientA.getSubsequentOwnExpenseList().size()).isEqualTo(1);
        assertThat(patientA.getSubsequentOwnExpenseList().get(0).getDisposalDate().isAfter(patientA.getDisposalDate())).isTrue();
        assertThat(patientA.getSubsequentDrugList().size()).isEqualTo(1);
        assertThat(patientA.getSubsequentDrugList().get(0).getDisposalDate().isAfter(patientA.getDisposalDate())).isTrue();

        List<EndoVo> patientBList = resultList.stream().filter(r -> r.getPatientId() == 2L).collect(toList());
        assertThat(patientBList.size()).isEqualTo(1);
        EndoVo patientB = patientBList.get(0);
        assertThat(patientB.getDisposalDate()).isEqualTo(LocalDate.of(2021, 1, 30));
        assertThat(patientB.getSubsequentNhiList().size()).isEqualTo(1);
        assertThat(patientB.getSubsequentNhiList().get(0).getDisposalDate().isAfter(patientB.getDisposalDate().plusMonths(gapMonths))).isTrue();
        assertThat(patientB.getSubsequentOwnExpenseList().size()).isEqualTo(1);
        assertThat(patientB.getSubsequentOwnExpenseList().get(0).getDisposalDate().isAfter(patientB.getDisposalDate().plusMonths(gapMonths))).isTrue();
        assertThat(patientB.getSubsequentDrugList().size()).isEqualTo(1);
        assertThat(patientB.getSubsequentDrugList().get(0).getDisposalDate().isAfter(patientB.getDisposalDate().plusMonths(gapMonths))).isTrue();
    }

    @Test
    public void testEndoReportDataFilterByDoctor() {
        int gapMonths = 3;
        LocalDate beginDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 2, 28);
        LocalDate dateAfter3monthsOfEndDate = endDate.plusMonths(gapMonths);
        List<NhiVo> dataList = getDataList();
        List<DisposalVo> disposalDataList = dataList.stream().map(ReportMapper.INSTANCE::mapToDisposalVo).collect(toList());
        List<NhiVo> availableList = dataList.stream().filter(d -> d.getDisposalDate().isAfter(beginDate) && d.getDisposalDate().isBefore(endDate)).collect(toList());
        List<Long> patientIds = availableList.stream().map(NhiVo::getPatientId).distinct().collect(toList());

        assertThat(availableList.size()).isEqualTo(4);
        assertThat(patientIds.size()).isEqualTo(3);
        assertThat(patientIds.contains(1L)).isTrue();
        assertThat(patientIds.contains(2L)).isTrue();
        assertThat(patientIds.contains(3L)).isTrue();

        when(reportDataRepository.findNhiVoBetweenAndCode(beginDate, endDate, codes)).thenReturn(availableList);
        when(reportDataRepository.findDisposalVoBetweenAndPatient(beginDate, dateAfter3monthsOfEndDate, patientIds)).thenReturn(disposalDataList);

        ReportDataProvider<EndoReportSetting, List<EndoVo>> provider = endoReportBuilderService.getDataProvider();
        FollowupBookSetting bookSetting = new FollowupBookSetting();
        bookSetting.setBeginDate(beginDate);
        bookSetting.setEndDate(endDate);
        bookSetting.setIncludeDoctorIds(new HashSet<>(asList(1L)));
        EndoReportSetting setting = new EndoReportSetting();
        setting.setBookSetting(bookSetting);
        setting.setFollowupGapMonths(gapMonths);
        List<EndoVo> resultList = provider.get(setting);

        assertThat(resultList.size()).isEqualTo(1);

        List<EndoVo> patientA = resultList.stream().filter(r -> r.getPatientId() == 1L).collect(toList());
        assertThat(patientA.size()).isEqualTo(1);
        assertThat(patientA.get(0).getDisposalDate()).isEqualTo(LocalDate.of(2021, 2, 24));
    }

}

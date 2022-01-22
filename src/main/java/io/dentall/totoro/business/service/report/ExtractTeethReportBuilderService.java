package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportMapper;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.*;
import io.dentall.totoro.business.service.report.followup.ExtractTeethReportSetting;
import io.dentall.totoro.business.service.report.followup.ExtractTeethSheet;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.ReportDataRepository;
import io.dentall.totoro.repository.ToothRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
public class ExtractTeethReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    private final ToothRepository toothRepository;

    public ExtractTeethReportBuilderService(ReportDataRepository reportDataRepository, AppointmentRepository appointmentRepository, ToothRepository toothRepository) {
        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
        this.toothRepository = toothRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new ExtractTeethSheet((ExtractTeethReportSetting) setting));
        return report;
    }

    public ReportDataProvider<ExtractTeethReportSetting, List<ExtractTeethVo>> getDataProvider() {
        return (setting) -> {
            int gapMonths = setting.getFollowupGapMonths();

            // 選定時間區間內有拔牙的病人，其區間內最後一次的拔牙治療
            Map<Long, List<NhiVo>> candidateList = getCandidateList(setting);
            // 取得gapMonths月內未回診的病人
            List<NhiVo> availableList = getAvailableList(setting, candidateList);
            // 三個月內未回診的病人id
            List<Long> patientIds = availableList.stream().map(NhiVo::getPatientId).distinct().collect(toList());

            LocalDate firstAvailableSubsequentDate = setting.getBeginDate().plusMonths(gapMonths).plusDays(1L);
            // 查找拔牙後gapMonths月的健保處置資料
            Map<Long, List<NhiVo>> subsequentNhiList = findSubsequentNhi(firstAvailableSubsequentDate, patientIds, reportDataRepository);
            // 查找拔牙後gapMonths月的自費處置資料
            Map<Long, List<OwnExpenseVo>> subsequentOwnExpenseList = findSubsequentOwnExpense(firstAvailableSubsequentDate, patientIds, reportDataRepository);
            // 查找拔牙後gapMonths月的藥品處置資料
            Map<Long, List<DrugVo>> subsequentDrugList = findSubsequentDrug(firstAvailableSubsequentDate, patientIds, reportDataRepository);

            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);

            return availableList
                .stream()
                .map(ReportMapper.INSTANCE::mapToExtractTeethVo)
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .peek(availableSubsequentNhiList(subsequentNhiList, gapMonths))
                .peek(availableSubsequentOwnExpenseList(subsequentOwnExpenseList, gapMonths, toothRepository))
                .peek(availableSubsequentDrugList(subsequentDrugList, gapMonths))
                .sorted(comparing(ExtractTeethVo::getDisposalDate))
                .collect(toList());
        };
    }

    private Map<Long, List<NhiVo>> getCandidateList(ExtractTeethReportSetting setting) {
        List<String> codes = asList("92013C", "92014C", "92015C", "92016C", "92055C", "92063C");
        Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
        return reportDataRepository.findNhiVoBetweenAndCode(setting.getBeginDate(), setting.getEndDate(), codes)
            .stream()
            .parallel()
            .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
            .collect(groupingBy(NhiVo::getPatientId, collectingAndThen(toList(), list -> {
                List<NhiVo> result = new ArrayList<>(0);
                Optional<NhiVo> max = list.stream().max(comparing(NhiVo::getDisposalDate));
                if (max.isPresent()) {
                    result = list.stream().filter(data -> data.getDisposalId().equals(max.get().getDisposalId())).collect(toList());
                }
                return result;
            })));
    }

    private List<NhiVo> getAvailableList(ExtractTeethReportSetting setting, Map<Long, List<NhiVo>> candidateList) {
        int gapMonths = setting.getFollowupGapMonths();
        return candidateList
            .values()
            .stream()
            .flatMap(Collection::stream)
            .filter(hasNoFollowupDisposalInGapMonths(
                setting.getBeginDate(),
                setting.getEndDate().plusMonths(gapMonths),
                new ArrayList<>(candidateList.keySet()),
                gapMonths,
                reportDataRepository))
            .collect(toList());
    }
}

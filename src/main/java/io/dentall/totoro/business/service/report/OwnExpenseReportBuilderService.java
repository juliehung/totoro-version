package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportMapper;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.DrugVo;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.OwnExpenseVo;
import io.dentall.totoro.business.service.report.followup.OwnExpenseReportSetting;
import io.dentall.totoro.business.service.report.followup.OwnExpenseSheet;
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
public class OwnExpenseReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    private final ToothRepository toothRepository;

    public OwnExpenseReportBuilderService(ReportDataRepository reportDataRepository, AppointmentRepository appointmentRepository, ToothRepository toothRepository) {
        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
        this.toothRepository = toothRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new OwnExpenseSheet((OwnExpenseReportSetting) setting));
        return report;
    }

    public ReportDataProvider<OwnExpenseReportSetting, List<OwnExpenseVo>> getDataProvider() {
        return (setting) -> {
            int gapMonths = setting.getFollowupGapMonths();

            // 選定時間區間內有自費的病人，其區間內最後一次的治療
            Map<Long, Optional<OwnExpenseVo>> candidateList = getCandidateList(setting);
            // 取得gapMonths月內未回診的病人
            List<OwnExpenseVo> availableList = getAvailableList(setting, candidateList);
            // 三個月內未回診的病人id
            List<Long> patientIds = availableList.stream().map(OwnExpenseVo::getPatientId).distinct().collect(toList());

            LocalDate firstAvailableSubsequentDate = setting.getBeginDate().plusMonths(gapMonths).plusDays(1L);
            // 查找根管後gapMonths月的健保處置資料
            Map<Long, List<NhiVo>> subsequentNhiList = findSubsequentNhi(firstAvailableSubsequentDate, patientIds, reportDataRepository);
            // 查找根管後gapMonths月的自費處置資料
            Map<Long, List<OwnExpenseVo>> subsequentOwnExpenseList = findSubsequentOwnExpense(firstAvailableSubsequentDate, patientIds, reportDataRepository);
            // 查找根管後gapMonths月的藥品處置資料
            Map<Long, List<DrugVo>> subsequentDrugList = findSubsequentDrug(firstAvailableSubsequentDate, patientIds, reportDataRepository);

            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);

            return availableList
                .stream()
                .map(ReportMapper.INSTANCE::mapToOwnExpenseVo)
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .peek(findTooth(toothRepository))
                .peek(availableSubsequentNhiList(subsequentNhiList, gapMonths))
                .peek(availableSubsequentOwnExpenseList(subsequentOwnExpenseList, gapMonths, toothRepository))
                .peek(availableSubsequentDrugList(subsequentDrugList, gapMonths))
                .sorted(comparing(OwnExpenseVo::getDisposalDate))
                .collect(toList());
        };
    }

    private Map<Long, Optional<OwnExpenseVo>> getCandidateList(OwnExpenseReportSetting setting) {
        List<Long> procedureIds = asList(setting.getProcedureId());
        Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
        return reportDataRepository.findOwnExpenseVoBetweenAndProcedureId(setting.getBeginDate(), setting.getEndDate(), procedureIds)
            .stream()
            .parallel()
            .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
            .collect(groupingBy(OwnExpenseVo::getPatientId, maxBy(comparing(OwnExpenseVo::getDisposalDate))));
    }

    private List<OwnExpenseVo> getAvailableList(OwnExpenseReportSetting setting, Map<Long, Optional<OwnExpenseVo>> candidateList) {
        int gapMonths = setting.getFollowupGapMonths();
        return candidateList
            .values()
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(hasNoFollowupDisposalInGapMonths(
                setting.getBeginDate(),
                setting.getEndDate().plusMonths(gapMonths),
                new ArrayList<>(candidateList.keySet()),
                gapMonths,
                reportDataRepository))
            .collect(toList());
    }
}

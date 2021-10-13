package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.TeethCleaningVo;
import io.dentall.totoro.business.service.report.followup.TeethCleaningReportSetting;
import io.dentall.totoro.business.service.report.followup.TeethCleaningSheet;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.ReportDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.futureAppointmentList;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
public class TeethCleaningReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    public TeethCleaningReportBuilderService(ReportDataRepository reportDataRepository, AppointmentRepository appointmentRepository) {
        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new TeethCleaningSheet((TeethCleaningReportSetting) setting));
        return report;
    }

    private ReportDataProvider<TeethCleaningReportSetting, List<TeethCleaningVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);

            // 查出每個病人最近的 91004C
            List<NhiVo> available = reportDataRepository.findAllPatientLatestNhiVo(singletonList("91004C"))
                .stream()
                .parallel()
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .filter(vo -> {
                    LocalDate availableData = vo.getDisposalDate().plus(181, DAYS);
                    return !availableData.isAfter(setting.getEndDate());
                }).collect(toList());

            // 分五組，讓查詢最多查5次
            AtomicInteger count = new AtomicInteger(1);
            Map<Integer, List<Long>> group = available
                .stream()
                .parallel()
                .map(NhiVo::getDisposalId)
                .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

            return group.values()
                .stream()
                .parallel()
                .map(reportDataRepository::findNhiVoDisposalIds)
                .flatMap(Collection::stream)
                .filter(vo -> "91004C".equals(vo.getProcedureCode()))
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .peek(vo -> vo.setNextAvailableTreatmentDate(vo.getDisposalDate().plus(181, DAYS)))
                .sorted((v1, v2) -> v2.getPatientBirth().compareTo(v1.getPatientBirth()))
                .collect(toList());
        };
    }
}

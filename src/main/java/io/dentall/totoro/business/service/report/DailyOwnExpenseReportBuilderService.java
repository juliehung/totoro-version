package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.dto.OwnExpenseVo;
import io.dentall.totoro.business.service.report.treatment.DailyOwnExpenseReportSetting;
import io.dentall.totoro.business.service.report.treatment.DailyOwnExpenseSheet;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.ReportDataRepository;
import io.dentall.totoro.repository.ToothRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.findTooth;
import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.futureAppointmentList;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
public class DailyOwnExpenseReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    private final ToothRepository toothRepository;

    public DailyOwnExpenseReportBuilderService(ReportDataRepository reportDataRepository, AppointmentRepository appointmentRepository, ToothRepository toothRepository) {
        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
        this.toothRepository = toothRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new DailyOwnExpenseSheet());
        return report;
    }

    private ReportDataProvider<DailyOwnExpenseReportSetting, List<OwnExpenseVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeOwnExpenseIds = ofNullable(setting.getIncludeOwnExpenseIds()).orElse(emptySet());
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);

            return reportDataRepository
                .findOwnExpenseVoBetween(setting.getBeginDate(), setting.getEndDate())
                .stream()
                .parallel()
                .filter(vo -> includeOwnExpenseIds.isEmpty() || includeOwnExpenseIds.contains(vo.getProcedureId()))
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .peek(findTooth(toothRepository))
                .sorted(comparing(OwnExpenseVo::getDisposalDate))
                .collect(toList());
        };
    }
}

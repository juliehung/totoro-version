package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.DrugVo;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.treatment.DailyDrugReportSetting;
import io.dentall.totoro.business.service.report.treatment.DailyDrugSheet;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.ReportDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.futureAppointmentList;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
public class DailyDrugReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    public DailyDrugReportBuilderService(ReportDataRepository reportDataRepository, AppointmentRepository appointmentRepository) {
        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new DailyDrugSheet());
        return report;
    }

    private ReportDataProvider<DailyDrugReportSetting, List<DrugVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeDrugIds = ofNullable(setting.getIncludeDrugIds()).orElse(emptySet());
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);

            return reportDataRepository
                .findDrugVoBetween(setting.getBeginDate(), setting.getEndDate())
                .stream()
                .parallel()
                .filter(vo -> includeDrugIds.isEmpty() || includeDrugIds.contains(vo.getDrugId()))
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .sorted(comparing(DrugVo::getDisposalDate))
                .collect(toList());
        };
    }
}

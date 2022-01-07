package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.followup.OdReportSetting;
import io.dentall.totoro.business.service.report.followup.OdSheet;
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
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.futureAppointmentList;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@Transactional(readOnly = true)
public class OdReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    public static final Set<String> CodesByOd = Stream.of(
        "89001C", "89002C", "89003C", "89004C", "89005C", "89008C", "89009C", "89010C", "89011C", "89012C"
    ).collect(toSet());

    public OdReportBuilderService(ReportDataRepository reportDataRepository, AppointmentRepository appointmentRepository) {
        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new OdSheet((OdReportSetting) setting));
        return report;
    }

    public ReportDataProvider<OdReportSetting, List<NhiVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);

            return reportDataRepository
                .findNhiVoBetween(setting.getBeginDate(), setting.getEndDate())
                .stream()
                .parallel()
                .filter(vo -> CodesByOd.contains(vo.getProcedureCode()))
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .sorted(comparing(NhiVo::getDisposalDate))
                .collect(toList());
        };
    }
}

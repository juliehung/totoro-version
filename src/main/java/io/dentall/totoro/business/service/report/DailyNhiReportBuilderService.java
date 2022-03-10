package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.treatment.DailyNhiReportSetting;
import io.dentall.totoro.business.service.report.treatment.DailyNhiSheet;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.NhiProcedureRepository;
import io.dentall.totoro.repository.ReportDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.futureAppointmentList;
import static io.dentall.totoro.business.service.report.context.ReportHelper.generateExamNhiVoList;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@Transactional(readOnly = true)
public class DailyNhiReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    private final NhiProcedureRepository nhiProcedureRepository;

    public DailyNhiReportBuilderService(
        ReportDataRepository reportDataRepository,
        AppointmentRepository appointmentRepository,
        NhiProcedureRepository nhiProcedureRepository) {

        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
        this.nhiProcedureRepository = nhiProcedureRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new DailyNhiSheet());
        return report;
    }

    public ReportDataProvider<DailyNhiReportSetting, List<NhiVo>> getDataProvider() {
        return (setting) -> {
            // 因為nhi_procedure會有相同code，但不同id的資料，所以不能用id來過濾資料(會有機率發生在同一個code下，只過濾到部份id的情況發生)，要用code來過慮資料才行
            Set<Long> includeNhiProcedureIds = ofNullable(setting.getIncludeNhiProcedureIds()).orElse(emptySet());
            Set<String> nhiCodeSet = includeNhiProcedureIds.isEmpty() ? emptySet() :
                nhiProcedureRepository.findAllByIdIn(new ArrayList<>(includeNhiProcedureIds)).stream().map(NhiProcedure::getCode).collect(toSet());

            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);
            List<NhiVo> nhiVoList = reportDataRepository.findNhiVoBetween(setting.getBeginDate(), setting.getEndDate());
            nhiVoList.addAll(generateExamNhiVoList(nhiVoList));

            return nhiVoList
                .stream()
                .parallel()
                .filter(vo -> nhiCodeSet.isEmpty() || nhiCodeSet.contains(vo.getProcedureCode()))
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .sorted(comparing(c1 -> String.valueOf(c1.getDisposalDate().toEpochDay()) + c1.getDisposalId()))
                .collect(toList());
        };
    }
}

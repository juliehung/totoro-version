package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportMapper;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.FutureAppointmentVo;
import io.dentall.totoro.business.service.report.dto.NhiDto;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.PeriodontalVo;
import io.dentall.totoro.business.service.report.followup.PeriodontalReportSetting;
import io.dentall.totoro.business.service.report.followup.PeriodontalSheet;
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
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
public class PeriodontalReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final AppointmentRepository appointmentRepository;

    public PeriodontalReportBuilderService(ReportDataRepository reportDataRepository, AppointmentRepository appointmentRepository) {
        this.reportDataRepository = reportDataRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new PeriodontalSheet((PeriodontalReportSetting) setting));
        return report;
    }

    public ReportDataProvider<PeriodontalReportSetting, List<PeriodontalVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());
            Instant todayBeginTime = LocalDate.now().atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant();
            Map<Long, List<FutureAppointmentVo>> futureAppointmentMap = new HashMap<>(50);
            List<String> codes = asList("91021C", "91022C", "91023C");
            List<NhiVo> available = reportDataRepository.findNhiVoBetweenAndCode(setting.getBeginDate(), setting.getEndDate(), codes)
                .stream()
                .parallel()
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .collect(toList());

            // 分五組，讓查詢最多查5次
            AtomicInteger count = new AtomicInteger(1);
            Map<Integer, List<Long>> group = available
                .stream()
                .parallel()
                .map(NhiVo::getPatientId)
                .distinct()
                .collect(groupingByConcurrent((key) -> count.getAndIncrement() % 5));

            return group.values()
                .stream()
                .parallel()
                .map(patientIds -> reportDataRepository.findNhiVoPatientAndCode(patientIds, codes))
                .flatMap(Collection::stream)
                .collect(groupingByConcurrent(NhiDto::getPatientId))
                .values()
                .stream()
                .map(nhiVoList -> {
                    LocalDate p4001Date = nhiVoList.stream().filter(dto -> "91021C".equals(dto.getProcedureCode())).findAny().map(NhiDto::getDisposalDate).orElse(null);
                    LocalDate p4002Date = nhiVoList.stream().filter(dto -> "91022C".equals(dto.getProcedureCode())).findAny().map(NhiDto::getDisposalDate).orElse(null);
                    LocalDate p4003Date = nhiVoList.stream().filter(dto -> "91023C".equals(dto.getProcedureCode())).findAny().map(NhiDto::getDisposalDate).orElse(null);
                    PeriodontalVo vo = ReportMapper.INSTANCE.mapToPeriodontalVo(nhiVoList.get(0));
                    vo.setP4001Date(p4001Date);
                    vo.setP4002Date(p4002Date);
                    vo.setP4003Date(p4003Date);
                    return vo;
                })
                .peek(futureAppointmentList(futureAppointmentMap, todayBeginTime, appointmentRepository))
                .sorted((v1, v2) -> v2.getPatientBirth().compareTo(v1.getPatientBirth()))
                .collect(toList());
        };
    }
}

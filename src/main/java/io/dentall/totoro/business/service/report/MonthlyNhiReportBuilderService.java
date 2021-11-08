package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.NhiVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyNhiVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyNhiVo.Summary;
import io.dentall.totoro.business.service.report.treatment.MonthlyNhiReportSetting;
import io.dentall.totoro.business.service.report.treatment.MonthlyNhiSheet;
import io.dentall.totoro.repository.ReportDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import static io.dentall.totoro.business.service.report.context.ReportHelper.calculatePtCount;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
public class MonthlyNhiReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    public MonthlyNhiReportBuilderService(ReportDataRepository reportDataRepository) {
        this.reportDataRepository = reportDataRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new MonthlyNhiSheet());
        return report;
    }

    public ReportDataProvider<MonthlyNhiReportSetting, List<SubjectMonthlyNhiVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeNhiProcedureIds = ofNullable(setting.getIncludeNhiProcedureIds()).orElse(emptySet());
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());

            List<NhiVo> nhiVoList = reportDataRepository
                .findNhiVoBetween(setting.getBeginMonth().atDay(1), setting.getEndMonth().atEndOfMonth())
                .stream()
                .parallel()
                .filter(vo -> includeNhiProcedureIds.isEmpty() || includeNhiProcedureIds.contains(vo.getProcedureId()))
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .collect(toList());

            Map<SubjectMonthlyNhiVo, Map<Summary, List<NhiVo>>> groupBySubject =
                nhiVoList
                    .stream()
                    .collect(groupingByConcurrent(SubjectMonthlyNhiVo::new, groupingBy(Summary::new)));

            if (includeDoctorIds.isEmpty()) {
                SubjectMonthlyNhiVo clinicMonthly = new SubjectMonthlyNhiVo(Long.MIN_VALUE, "全院所");
                Map<Summary, List<NhiVo>> clinicSummary = nhiVoList.stream().parallel().collect(groupingByConcurrent(Summary::new));
                groupBySubject.put(clinicMonthly, clinicSummary);
            }

            return groupBySubject
                .entrySet()
                .stream()
                .parallel()
                .map(processSubject())
                .sorted(comparing(SubjectMonthlyNhiVo::getSubjectId))
                .collect(toList());
        };
    }

    private Function<Entry<SubjectMonthlyNhiVo, Map<Summary, List<NhiVo>>>, SubjectMonthlyNhiVo> processSubject() {
        return (entry) -> {
            SubjectMonthlyNhiVo monthlyNhiVo = entry.getKey();
            Map<Summary, List<NhiVo>> groupByMonthCode = entry.getValue();

            List<Summary> summaryList = groupByMonthCode
                .entrySet()
                .stream()
                .parallel()
                .map(processSummary())
                .sorted(comparing(summary -> summary.getDisposalMonth() + summary.getProcedureCode()))
                .collect(toList());

            monthlyNhiVo.setSummaryList(summaryList);
            return monthlyNhiVo;
        };
    }

    private Function<Entry<Summary, List<NhiVo>>, Summary> processSummary() {
        return (entry) -> {
            Summary summary = entry.getKey();
            List<NhiVo> nhiVoList = entry.getValue();
            Set<String> existPatientCardNumber = new HashSet<>();
            Set<Long> existDisposal = new HashSet<>();

            return nhiVoList.stream().reduce(summary, (summaryRef, vo) -> {
                    summaryRef.setPatientCount(
                        calculatePtCount(
                            vo.getDisposalId(),
                            vo.getDisposalDate(),
                            vo.getPatientId(),
                            vo.getCardNumber(),
                            summaryRef.getPatientCount(),
                            existPatientCardNumber,
                            existDisposal));
                    summaryRef.setProcedureCount(summaryRef.getProcedureCount() + 1);
                    summaryRef.setProcedurePoint(summaryRef.getProcedurePoint() + vo.getProcedurePoint());
                    return summaryRef;
                },
                ((summary1, summary2) -> {
                    summary1.setPatientCount(summary1.getPatientCount() + summary2.getPatientCount());
                    summary1.setProcedureCount(summary1.getProcedureCount() + summary2.getProcedureCount());
                    summary1.setProcedurePoint(summary1.getProcedurePoint() + summary2.getProcedurePoint());
                    return summary1;
                }));
        };
    }
}

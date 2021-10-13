package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.OwnExpenseVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyOwnExpenseVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyOwnExpenseVo.Summary;
import io.dentall.totoro.business.service.report.treatment.MonthlyOwnExpenseReportSetting;
import io.dentall.totoro.business.service.report.treatment.MonthlyOwnExpenseSheet;
import io.dentall.totoro.repository.ReportDataRepository;
import io.dentall.totoro.repository.ToothRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.util.ToothUtil.splitA74;
import static io.dentall.totoro.business.service.report.context.ReportBuilderHelper.findTooth;
import static io.dentall.totoro.business.service.report.context.ReportHelper.calculatePtCount;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
public class MonthlyOwnExpenseReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    private final ToothRepository toothRepository;

    public MonthlyOwnExpenseReportBuilderService(ReportDataRepository reportDataRepository, ToothRepository toothRepository) {
        this.reportDataRepository = reportDataRepository;
        this.toothRepository = toothRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new MonthlyOwnExpenseSheet());
        return report;
    }

    private ReportDataProvider<MonthlyOwnExpenseReportSetting, List<SubjectMonthlyOwnExpenseVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeOwnExpenseIds = ofNullable(setting.getIncludeOwnExpenseIds()).orElse(emptySet());
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());

            List<OwnExpenseVo> ownExpenseVoList = reportDataRepository
                .findOwnExpenseVoBetween(setting.getBeginMonth().atDay(1), setting.getEndMonth().atEndOfMonth())
                .stream()
                .parallel()
                .filter(vo -> includeOwnExpenseIds.isEmpty() || includeOwnExpenseIds.contains(vo.getProcedureId()))
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .peek(findTooth(toothRepository))
                .collect(toList());

            Map<SubjectMonthlyOwnExpenseVo, Map<Summary, List<OwnExpenseVo>>> groupBySubject =
                ownExpenseVoList
                    .stream()
                    .collect(groupingByConcurrent(SubjectMonthlyOwnExpenseVo::new, groupingBy(Summary::new)));

            if (includeDoctorIds.isEmpty()) {
                SubjectMonthlyOwnExpenseVo clinicMonthly = new SubjectMonthlyOwnExpenseVo(Long.MIN_VALUE, "全院所");
                Map<Summary, List<OwnExpenseVo>> clinicSummary =
                    ownExpenseVoList.stream().parallel().collect(groupingByConcurrent(Summary::new));
                groupBySubject.put(clinicMonthly, clinicSummary);
            }

            return groupBySubject
                .entrySet()
                .stream()
                .parallel()
                .map(processSubject())
                .sorted(comparing(SubjectMonthlyOwnExpenseVo::getSubjectId))
                .collect(toList());
        };
    }

    private Function<Map.Entry<SubjectMonthlyOwnExpenseVo, Map<Summary, List<OwnExpenseVo>>>, SubjectMonthlyOwnExpenseVo> processSubject() {
        return (entry) -> {
            SubjectMonthlyOwnExpenseVo monthlyNhiVo = entry.getKey();
            Map<Summary, List<OwnExpenseVo>> groupByMonthCode = entry.getValue();

            List<Summary> summaryList = groupByMonthCode
                .entrySet()
                .stream()
                .parallel()
                .map(processSummary())
                .sorted((summary1, summary2) -> {
                        int c = summary1.getDisposalMonth().compareTo(summary2.getDisposalMonth());
                        if (c == 0) {
                            return Long.compare(summary1.getProcedureId(), summary2.getProcedureId());
                        } else {
                            return c;
                        }
                    }
                )
                .collect(toList());

            monthlyNhiVo.setSummaryList(summaryList);
            return monthlyNhiVo;
        };
    }

    private Function<Map.Entry<Summary, List<OwnExpenseVo>>, Summary> processSummary() {
        return (entry) -> {
            Summary summary = entry.getKey();
            List<OwnExpenseVo> ownExpenseVoList = entry.getValue();
            Set<String> existPatientCardNumber = new HashSet<>();
            Set<Long> existDisposal = new HashSet<>();

            return ownExpenseVoList.stream().reduce(summary, (summaryRef, vo) -> {
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
                    summaryRef.setToothCount(summaryRef.getToothCount() + splitA74(vo.getProcedureTooth()).size());
                    return summaryRef;
                },
                ((summary1, summary2) -> {
                    summary1.setPatientCount(summary1.getPatientCount() + summary2.getPatientCount());
                    summary1.setProcedureCount(summary1.getProcedureCount() + summary2.getProcedureCount());
                    summary1.setToothCount(summary1.getToothCount() + summary2.getToothCount());
                    return summary1;
                }));
        };
    }
}

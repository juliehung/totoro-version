package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.ExcelReport;
import io.dentall.totoro.business.service.report.context.ReportDataProvider;
import io.dentall.totoro.business.service.report.context.ReportSetting;
import io.dentall.totoro.business.service.report.dto.DrugVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyDrugVo;
import io.dentall.totoro.business.service.report.dto.SubjectMonthlyDrugVo.Summary;
import io.dentall.totoro.business.service.report.treatment.MonthlyDrugReportSetting;
import io.dentall.totoro.business.service.report.treatment.MonthlyDrugSheet;
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
public class MonthlyDrugReportBuilderService implements ReportBuilderService {

    private final ReportDataRepository reportDataRepository;

    public MonthlyDrugReportBuilderService(ReportDataRepository reportDataRepository) {
        this.reportDataRepository = reportDataRepository;
    }

    @Override
    public ExcelReport build(ReportSetting setting) {
        ExcelReport report = new ExcelReport(setting);
        report.setDataProvider(getDataProvider());
        report.bind(new MonthlyDrugSheet());
        return report;
    }

    private ReportDataProvider<MonthlyDrugReportSetting, List<SubjectMonthlyDrugVo>> getDataProvider() {
        return (setting) -> {
            Set<Long> includeDrugIds = ofNullable(setting.getIncludeDrugIds()).orElse(emptySet());
            Set<Long> includeDoctorIds = ofNullable(setting.getIncludeDoctorIds()).orElse(emptySet());

            List<DrugVo> drugVoList = reportDataRepository
                .findDrugVoBetween(setting.getBeginMonth().atDay(1), setting.getEndMonth().atEndOfMonth())
                .stream()
                .parallel()
                .filter(vo -> includeDrugIds.isEmpty() || includeDrugIds.contains(vo.getDrugId()))
                .filter(vo -> includeDoctorIds.isEmpty() || includeDoctorIds.contains(vo.getDoctorId()))
                .collect(toList());

            Map<SubjectMonthlyDrugVo, Map<Summary, List<DrugVo>>> groupBySubject =
                drugVoList
                    .stream()
                    .collect(groupingByConcurrent(SubjectMonthlyDrugVo::new, groupingBy(Summary::new)));

            if (includeDoctorIds.isEmpty()) {
                SubjectMonthlyDrugVo clinicMonthly = new SubjectMonthlyDrugVo(Long.MIN_VALUE, "全院所");
                Map<Summary, List<DrugVo>> clinicSummary = drugVoList.stream().parallel().collect(groupingByConcurrent(Summary::new));
                groupBySubject.put(clinicMonthly, clinicSummary);
            }

            return groupBySubject
                .entrySet()
                .stream()
                .parallel()
                .map(processSubject())
                .sorted(comparing(SubjectMonthlyDrugVo::getSubjectId))
                .collect(toList());
        };
    }

    private Function<Entry<SubjectMonthlyDrugVo, Map<Summary, List<DrugVo>>>, SubjectMonthlyDrugVo> processSubject() {
        return (entry) -> {
            SubjectMonthlyDrugVo monthlyNhiVo = entry.getKey();
            Map<Summary, List<DrugVo>> groupByMonthCode = entry.getValue();

            List<Summary> summaryList = groupByMonthCode
                .entrySet()
                .stream()
                .parallel()
                .map(processSummary())
                .sorted((summary1, summary2) -> {
                        int c = summary1.getDisposalMonth().compareTo(summary2.getDisposalMonth());
                        if (c == 0) {
                            return Long.compare(summary1.getDrugId(), summary2.getDrugId());
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

    private Function<Entry<Summary, List<DrugVo>>, Summary> processSummary() {
        return (entry) -> {
            Summary summary = entry.getKey();
            List<DrugVo> drugVoList = entry.getValue();
            Set<String> existPatientCardNumber = new HashSet<>();
            Set<Long> existDisposal = new HashSet<>();

            return drugVoList.stream().reduce(summary, (summaryRef, vo) -> {
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
                    return summaryRef;
                },
                ((summary1, summary2) -> {
                    summary1.setPatientCount(summary1.getPatientCount() + summary2.getPatientCount());
                    summary1.setProcedureCount(summary1.getProcedureCount() + summary2.getProcedureCount());
                    return summary1;
                }));
        };
    }
}

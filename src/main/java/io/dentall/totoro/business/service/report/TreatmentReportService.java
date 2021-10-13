package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.*;
import io.dentall.totoro.business.service.report.treatment.*;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.Drug;
import io.dentall.totoro.domain.ReportRecord;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.table.NhiProcedureTable;
import io.dentall.totoro.service.dto.table.ProcedureTable;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.vm.TreatmentReportVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.function.Function;

import static io.dentall.totoro.business.service.report.context.ReportCategory.TREATMENT;
import static io.dentall.totoro.business.service.report.context.ReportHelper.displayDisposalDate;
import static io.dentall.totoro.business.service.report.context.ReportHelper.getReportList;
import static io.dentall.totoro.domain.enumeration.BackupFileCatalog.TREATMENT_REPORT;
import static io.dentall.totoro.domain.enumeration.BatchStatus.CANCEL;
import static io.dentall.totoro.service.util.DateTimeUtil.formatToMinguoDate;
import static java.lang.String.join;
import static java.util.Collections.emptySet;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Service
public class TreatmentReportService implements ReportService, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(TreatmentReportService.class);

    private ApplicationContext applicationContext;

    private final NhiProcedureRepository nhiProcedureRepository;

    private final ProcedureRepository procedureRepository;

    private final DrugRepository drugRepository;

    private final UserRepository userRepository;

    private final ReportRecordRepository reportRecordRepository;

    private final String gcsBaseUrl;

    private final String bucketName;

    public TreatmentReportService(
        @Value("${gcp.gcs-base-url}") String gcsBaseUrl,
        @Value("${gcp.bucket-name}") String bucketName,
        NhiProcedureRepository nhiProcedureRepository,
        ProcedureRepository procedureRepository,
        DrugRepository drugRepository,
        UserRepository userRepository,
        ReportRecordRepository reportRecordRepository) {
        this.gcsBaseUrl = gcsBaseUrl;
        this.bucketName = bucketName;
        this.nhiProcedureRepository = nhiProcedureRepository;
        this.procedureRepository = procedureRepository;
        this.drugRepository = drugRepository;
        this.userRepository = userRepository;
        this.reportRecordRepository = reportRecordRepository;
    }

    public List<TreatmentReportVM> getReport(String treatmentType, String treatmentId) {
        List<ReportRecord> result = reportRecordRepository.findTreatmentReport(treatmentType, treatmentId);
        List<TreatmentReportVM> vmList = ReportVmMapper.INSTANCE.mapToTreatmentReportVM(result);
        vmList.forEach(vm -> vm.setUrl(gcsBaseUrl + "/" + bucketName + "/"));
        return vmList;
    }

    public Optional<TreatmentReportVM> cancelReport(long id) {
        Optional<ReportRecord> optional = reportRecordRepository.findById(id);
        if (optional.isPresent()) {
            ReportRecord record = optional.get();
            record.setStatus(CANCEL);
            reportRecordRepository.save(record);
            return Optional.of(ReportVmMapper.INSTANCE.mapToTreatmentReportVM(record));
        } else {
            return Optional.empty();
        }
    }

    @ReportRunner(category = TREATMENT, backupFileCatalog = TREATMENT_REPORT)
    public void createReport(TreatmentBookSetting treatmentBookSetting, ByteArrayOutputStream outputStream) throws Exception {
        ExcelBook book = new ExcelBook(outputStream);
        getReportList(treatmentBookSetting, applicationContext)
            .stream()
            .map(report -> (ExcelReport) report)
            .forEach(report -> report.bind(book));
        book.publish();
    }

    @Override
    public void completeSetting(BookSetting setting) {
        TreatmentBookSetting bookSetting = (TreatmentBookSetting) setting;
        Set<Long> includeTreatmentIds = ofNullable(bookSetting.getIncludeTreatmentIds()).orElse(emptySet());
        Set<String> includeTreatments = includeTreatmentIds.stream()
            .map(itemNameFunction(bookSetting))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toSet());
        bookSetting.setIncludeTreatmentCodes(includeTreatments);
    }

    @Override
    public String getBookFileName(BookSetting setting) {
        TreatmentBookSetting bookSetting = (TreatmentBookSetting) setting;
        Set<String> includeTreatmentCodes = ofNullable(bookSetting.getIncludeTreatmentCodes()).orElse(emptySet());
        String fileName;

        if (includeTreatmentCodes.isEmpty()) {
            if (nonNull(bookSetting.getDailyNhiReportSetting()) || nonNull(bookSetting.getMonthlyNhiReportSetting())) {
                fileName = "all nhi";
            } else if (nonNull(bookSetting.getDailyOwnExpenseReportSetting()) || nonNull(bookSetting.getMonthlyOwnExpenseReportSetting())) {
                fileName = "all own expense";
            } else if (nonNull(bookSetting.getDailyDrugReportSetting()) || nonNull(bookSetting.getMonthlyDrugReportSetting())) {
                fileName = "all medicine";
            } else {
                fileName = "unknown";
            }
        } else {
            fileName = join("-", includeTreatmentCodes);
        }

        if (nonNull(bookSetting.getDailyNhiReportSetting())
            || nonNull(bookSetting.getDailyOwnExpenseReportSetting())
            || nonNull(bookSetting.getDailyDrugReportSetting())) {
            fileName += "_daily_T"
                + formatToMinguoDate(bookSetting.getBeginDate(), "yyyMMdd") + "-" + formatToMinguoDate(bookSetting.getEndDate(), "yyyMMdd");
        } else if (nonNull(bookSetting.getMonthlyNhiReportSetting())
            || nonNull(bookSetting.getMonthlyOwnExpenseReportSetting())
            || nonNull(bookSetting.getMonthlyDrugReportSetting())) {
            fileName += "_monthly_T"
                + formatToMinguoDate(bookSetting.getBeginDate(), "yyyMM") + "-" + formatToMinguoDate(bookSetting.getEndDate(), "yyyMM");
        } else {
            fileName += "_unknown";
        }

        fileName += "_D" + DateTimeUtil.formatToMinguoDate(bookSetting.getExportTime().atOffset(TimeConfig.ZONE_OFF_SET), "yyyMMdd HH:mm:ss");
        fileName += ".xlsx";

        return fileName;
    }

    @Override
    public Map<String, String> getReportAttribute(BookSetting setting) {
        TreatmentBookSetting bookSetting = (TreatmentBookSetting) setting;
        String date = "";
        String doctor = "";
        String item = getItemNameAttr(bookSetting.getIncludeTreatmentCodes());
        String exportTime = formatToMinguoDate(bookSetting.getExportTime().atOffset(TimeConfig.ZONE_OFF_SET), "yyy/MM/dd HH:mm");
        String treatmentType = "";
        String treatmentId = isNull(bookSetting.getIncludeTreatmentIds()) || bookSetting.getIncludeTreatmentIds().isEmpty() ? "all" :
            bookSetting.getIncludeTreatmentIds().stream().map(String::valueOf).collect(joining("/"));

        if (nonNull(bookSetting.getDailyNhiReportSetting())) {
            DailyNhiReportSetting reportSetting = bookSetting.getDailyNhiReportSetting();
            date = displayDisposalDate(reportSetting.getBeginDate()) + "~" + displayDisposalDate(reportSetting.getEndDate());
            doctor = getDoctorNameAttr(reportSetting.getIncludeDoctorIds());
            treatmentType = "NHI";
        } else if (nonNull(bookSetting.getDailyOwnExpenseReportSetting())) {
            DailyOwnExpenseReportSetting reportSetting = bookSetting.getDailyOwnExpenseReportSetting();
            date = displayDisposalDate(reportSetting.getBeginDate()) + "~" + displayDisposalDate(reportSetting.getEndDate());
            doctor = getDoctorNameAttr(reportSetting.getIncludeDoctorIds());
            treatmentType = "OWN_EXPENSE";
        } else if (nonNull(bookSetting.getDailyDrugReportSetting())) {
            DailyDrugReportSetting reportSetting = bookSetting.getDailyDrugReportSetting();
            date = displayDisposalDate(reportSetting.getBeginDate()) + "~" + displayDisposalDate(reportSetting.getEndDate());
            doctor = getDoctorNameAttr(reportSetting.getIncludeDoctorIds());
            treatmentType = "DRUG";
        } else if (nonNull(bookSetting.getMonthlyNhiReportSetting())) {
            MonthlyNhiReportSetting reportSetting = bookSetting.getMonthlyNhiReportSetting();
            date = displayDisposalDate(reportSetting.getBeginMonth()) + "~" + displayDisposalDate(reportSetting.getEndMonth());
            doctor = getDoctorNameAttr(reportSetting.getIncludeDoctorIds());
            treatmentType = "NHI";
        } else if (nonNull(bookSetting.getMonthlyOwnExpenseReportSetting())) {
            MonthlyOwnExpenseReportSetting reportSetting = bookSetting.getMonthlyOwnExpenseReportSetting();
            date = displayDisposalDate(reportSetting.getBeginMonth()) + "~" + displayDisposalDate(reportSetting.getEndMonth());
            doctor = getDoctorNameAttr(reportSetting.getIncludeDoctorIds());
            treatmentType = "OWN_EXPENSE";
        } else if (nonNull(bookSetting.getMonthlyDrugReportSetting())) {
            MonthlyDrugReportSetting reportSetting = bookSetting.getMonthlyDrugReportSetting();
            date = displayDisposalDate(reportSetting.getBeginMonth()) + "~" + displayDisposalDate(reportSetting.getEndMonth());
            doctor = getDoctorNameAttr(reportSetting.getIncludeDoctorIds());
            treatmentType = "DRUG";
        }

        Map<String, String> attrs = new HashMap<>();
        attrs.put("date", date);
        attrs.put("doctor", doctor);
        attrs.put("item", item);
        attrs.put("exportTime", exportTime);
        attrs.put("treatmentType", treatmentType);
        attrs.put("treatmentId", treatmentId);

        return attrs;
    }

    private String getDoctorNameAttr(Set<Long> doctorIds) {
        return isNull(doctorIds) || doctorIds.isEmpty() ? "全院所" :
            doctorIds
                .stream()
                .sorted()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(User::getFirstName)
                .collect(joining("、"));
    }

    private String getItemNameAttr(Set<String> includeNhiProcedureCodes) {
        return isNull(includeNhiProcedureCodes) || includeNhiProcedureCodes.isEmpty() ? "全部項目" :
            join("、", includeNhiProcedureCodes);
    }

    private Function<Long, Optional<String>> itemNameFunction(TreatmentBookSetting treatmentBookSetting) {
        return (id) -> {
            if (nonNull(treatmentBookSetting.getDailyNhiReportSetting()) || nonNull(treatmentBookSetting.getMonthlyNhiReportSetting())) {
                return nhiProcedureRepository.findNhiProcedureById(id).map(NhiProcedureTable::getCode);
            } else if (nonNull(treatmentBookSetting.getDailyOwnExpenseReportSetting()) || nonNull(treatmentBookSetting.getMonthlyOwnExpenseReportSetting())) {
                return procedureRepository.findProcedureById(id).map(ProcedureTable::getContent);
            } else if (nonNull(treatmentBookSetting.getDailyDrugReportSetting()) || nonNull(treatmentBookSetting.getMonthlyDrugReportSetting())) {
                return drugRepository.findById(id).map(Drug::getName);
            }
            return Optional.of("unknown");
        };
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

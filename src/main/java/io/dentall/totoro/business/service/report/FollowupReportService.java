package io.dentall.totoro.business.service.report;

import io.dentall.totoro.business.service.report.context.*;
import io.dentall.totoro.business.service.report.followup.FollowupBookSetting;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.ReportRecord;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.ProcedureRepository;
import io.dentall.totoro.repository.ReportRecordRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.dto.table.ProcedureTable;
import io.dentall.totoro.web.rest.vm.FollowupReportVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.*;

import static io.dentall.totoro.business.service.report.context.ReportCategory.FOLLOWUP;
import static io.dentall.totoro.business.service.report.context.ReportHelper.displayDisposalDate;
import static io.dentall.totoro.business.service.report.context.ReportHelper.getReportList;
import static io.dentall.totoro.domain.enumeration.BackupFileCatalog.FOLLOWUP_REPORT;
import static io.dentall.totoro.domain.enumeration.BatchStatus.CANCEL;
import static io.dentall.totoro.service.util.DateTimeUtil.formatToMinguoDate;
import static java.lang.String.join;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

@Service
public class FollowupReportService implements ReportService, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(TreatmentReportService.class);

    private ApplicationContext applicationContext;

    private final ProcedureRepository procedureRepository;

    private final UserRepository userRepository;

    private final ReportRecordRepository reportRecordRepository;

    private final String gcsBaseUrl;

    private final String bucketName;

    public FollowupReportService(
        @Value("${gcp.gcs-base-url}") String gcsBaseUrl,
        @Value("${gcp.bucket-name}") String bucketName,
        ProcedureRepository procedureRepository,
        UserRepository userRepository,
        ReportRecordRepository reportRecordRepository) {
        this.gcsBaseUrl = gcsBaseUrl;
        this.bucketName = bucketName;
        this.procedureRepository = procedureRepository;
        this.userRepository = userRepository;
        this.reportRecordRepository = reportRecordRepository;
    }

    public List<FollowupReportVM> getReport() {
        List<ReportRecord> result = reportRecordRepository.findByCategoryOrderByCreatedDateDesc(FOLLOWUP);
        List<FollowupReportVM> vmList = ReportVmMapper.INSTANCE.mapToFollowupReportVM(result);
        vmList.forEach(vm -> vm.setUrl(gcsBaseUrl + "/" + bucketName + "/"));
        return vmList;
    }

    public Optional<FollowupReportVM> cancelReport(long id) {
        Optional<ReportRecord> optional = reportRecordRepository.findById(id);
        if (optional.isPresent()) {
            ReportRecord record = optional.get();
            record.setStatus(CANCEL);
            reportRecordRepository.save(record);
            return Optional.of(ReportVmMapper.INSTANCE.mapToFollowupReportVM(record));
        } else {
            return Optional.empty();
        }
    }

    @ReportRunner(category = FOLLOWUP, backupFileCatalog = FOLLOWUP_REPORT)
    public void createReport(FollowupBookSetting followupBookSetting, ByteArrayOutputStream outputStream) throws Exception {
        ExcelBook book = new ExcelBook(outputStream);
        getReportList(followupBookSetting, applicationContext)
            .stream()
            .map(report -> (ExcelReport) report)
            .forEach(report -> report.bind(book));
        book.publish();
    }

    @Override
    public void completeSetting(BookSetting setting) {
        FollowupBookSetting bookSetting = (FollowupBookSetting) setting;
        if (nonNull(bookSetting.getOwnExpenseReportSetting())) {
            long procedureId = bookSetting.getOwnExpenseReportSetting().getProcedureId();
            bookSetting.getOwnExpenseReportSetting().setProcedureName(procedureRepository.findProcedureById(procedureId).map(ProcedureTable::getContent).orElse(""));
        }
    }

    @Override
    public String getBookFileName(BookSetting setting) {
        FollowupBookSetting bookSetting = (FollowupBookSetting) setting;
        return "revisit_T" + formatToMinguoDate(bookSetting.getBeginDate(), "yyyMMdd") + "-" + formatToMinguoDate(bookSetting.getEndDate(), "yyyMMdd")
            + "_D" + formatToMinguoDate(Instant.now().atOffset(TimeConfig.ZONE_OFF_SET), "yyyMMdd HH:mm:ss") + ".xlsx";
    }

    @Override
    public Map<String, String> getReportAttribute(BookSetting setting) {
        FollowupBookSetting bookSetting = (FollowupBookSetting) setting;
        String date = displayDisposalDate(bookSetting.getBeginDate()) + "~" + displayDisposalDate(bookSetting.getEndDate());
        String doctor = getDoctorNameAttr(bookSetting.getIncludeDoctorIds());
        String item = getItemNameAttr(bookSetting);
        String exportTime = formatToMinguoDate(bookSetting.getExportTime().atOffset(TimeConfig.ZONE_OFF_SET), "yyy/MM/dd HH:mm");

        Map<String, String> attrs = new HashMap<>();
        attrs.put("date", date);
        attrs.put("doctor", doctor);
        attrs.put("item", item);
        attrs.put("exportTime", exportTime);

        return attrs;
    }

    private String getItemNameAttr(FollowupBookSetting bookSetting) {
        List<String> sheetNames = new ArrayList<>();
        if (nonNull(bookSetting.getTeethCleaningReportSetting())) {
            sheetNames.add(bookSetting.getTeethCleaningReportSetting().getSheetName());
        }
        if (nonNull(bookSetting.getFluoridationReportSetting())) {
            sheetNames.add(bookSetting.getFluoridationReportSetting().getSheetName());
        }
        if (nonNull(bookSetting.getOdReportSetting())) {
            sheetNames.add(bookSetting.getOdReportSetting().getSheetName());
        }
        if (nonNull(bookSetting.getPeriodontalReportSetting())) {
            sheetNames.add(bookSetting.getPeriodontalReportSetting().getSheetName());
        }
        if (nonNull(bookSetting.getExtractTeethReportSetting())) {
            sheetNames.add(bookSetting.getExtractTeethReportSetting().getSheetName());
        }
        if (nonNull(bookSetting.getEndoReportSetting())) {
            sheetNames.add(bookSetting.getEndoReportSetting().getSheetName());
        }
        if (nonNull(bookSetting.getOwnExpenseReportSetting())) {
            sheetNames.add(bookSetting.getOwnExpenseReportSetting().getSheetName());
        }

        return join("、", sheetNames);
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

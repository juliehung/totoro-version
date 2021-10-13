package io.dentall.totoro.aop;

import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.report.ReportService;
import io.dentall.totoro.business.service.report.context.BookSetting;
import io.dentall.totoro.business.service.report.context.ReportCategory;
import io.dentall.totoro.business.service.report.context.ReportRunner;
import io.dentall.totoro.business.service.report.context.ReportRunningCheck;
import io.dentall.totoro.domain.ReportRecord;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import io.dentall.totoro.repository.ReportRecordRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static io.dentall.totoro.domain.enumeration.BatchStatus.DONE;
import static io.dentall.totoro.domain.enumeration.BatchStatus.FAILURE;
import static java.util.Objects.isNull;

@Aspect
public class ReportAspect {

    private static final Logger log = LoggerFactory.getLogger(ReportAspect.class);

    private final ImageGcsBusinessService imageGcsBusinessService;

    private final ReportRecordRepository reportRecordRepository;

    public ReportAspect(ImageGcsBusinessService imageGcsBusinessService, ReportRecordRepository reportRecordRepository) {
        this.imageGcsBusinessService = imageGcsBusinessService;
        this.reportRecordRepository = reportRecordRepository;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerPointcut() {
    }

    @Pointcut("within(io.dentall.totoro.web.rest..*)")
    public void restPackagePointcut() {
    }

    @Pointcut("@annotation(reportRunningCheck)")
    public void runningCheckPointcut(ReportRunningCheck reportRunningCheck) {
        ReportCategory category = reportRunningCheck.category();
    }

    @Around("restPackagePointcut() && restControllerPointcut() && runningCheckPointcut(reportRunningCheck)")
    public Object logAround(ProceedingJoinPoint joinPoint, ReportRunningCheck reportRunningCheck) throws Throwable {
        ReportCategory category = reportRunningCheck.category();
        int upLimit = reportRunningCheck.concurrentUpLimit();
        int lockCount = reportRecordRepository.countByCategoryAndStatus(category, BatchStatus.LOCK);

        if (upLimit > 0 && lockCount < upLimit) {
            return joinPoint.proceed();
        } else {
            return ResponseEntity.badRequest().body(String.format("%s concurrent export amount is %s, maximum limit is %s", category, lockCount, upLimit));
        }
    }

    @Pointcut("@annotation(reportRunner) && target(service) && args(bookSetting, outputStream)")
    public void ReportPointcut(ReportRunner reportRunner, ReportService service, BookSetting bookSetting, ByteArrayOutputStream outputStream) {
    }

    @Around("ReportPointcut(reportRunner, service, bookSetting, outputStream)")
    public Object handle(ProceedingJoinPoint joinPoint, ReportRunner reportRunner, ReportService service, BookSetting bookSetting, ByteArrayOutputStream outputStream) throws Throwable {
        service.completeSetting(bookSetting);
        ReportRecord reportRecord = newReportRecord(reportRunner, service, bookSetting);
        String fileName = reportRecord.getFileName();
        Object result = null;
        Exception exception = null;

        try {
            log.info("[REPORT] Create " + fileName + " start");
            result = joinPoint.proceed();
            uploadFile(reportRunner, reportRecord, outputStream);
            log.info("[REPORT] Create " + fileName + " end");
        } catch (Exception e) {
            exception = e;
            log.error("[REPORT] Create " + fileName + " fail", e);
        } finally {
            if (isNull(exception)) {
                reportDone(reportRecord);
            } else {
                reportFails(reportRecord, exception);
            }
        }

        return result;
    }

    private ReportRecord newReportRecord(ReportRunner reportRunner, ReportService service, BookSetting bookSetting) {
        String filePath = imageGcsBusinessService.getClinicName().concat("/").concat(reportRunner.backupFileCatalog().getRemotePath()).concat("/");
        String fileName = service.getBookFileName(bookSetting);
        Map<String, String> attrs = service.getReportAttribute(bookSetting);
        ReportRecord reportRecord = new ReportRecord();
        reportRecord.setCategory(reportRunner.category());
        reportRecord.setStatus(BatchStatus.LOCK);
        reportRecord.setAttrs(attrs);
        reportRecord.setFilePath(filePath);
        reportRecord.setFileName(fileName);
        return reportRecordRepository.save(reportRecord);
    }

    private void reportDone(ReportRecord reportRecord) {
        try {
            reportRecord.setStatus(DONE);
            reportRecordRepository.save(reportRecord);
        } catch (Exception e) {
            log.error("[REPORT] Save Report Record Fail", e);
            reportRecord.setStatus(FAILURE);
            reportRecord.setComment("Save Report Record Fail: " + e);
            reportRecordRepository.save(reportRecord);
        }
    }

    private void reportFails(ReportRecord reportRecord, Exception exception) {
        try {
            reportRecord.setStatus(FAILURE);
            reportRecord.setComment(exception.toString());
            reportRecordRepository.save(reportRecord);
        } catch (Exception e) {
            log.error("[REPORT] Save Report Record Fail", e);
            reportRecord.setStatus(FAILURE);
            reportRecord.setComment("Save Report Record Fail: " + e);
            reportRecordRepository.save(reportRecord);
        }
    }

    private void uploadFile(ReportRunner reportRunner, ReportRecord reportRecord, ByteArrayOutputStream outputStream) {
        imageGcsBusinessService.uploadFile(
            reportRecord.getFilePath(),
            reportRecord.getFileName(),
            outputStream.toByteArray(),
            reportRunner.backupFileCatalog().getFileExtension()
        );
    }

}

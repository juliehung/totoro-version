package io.dentall.totoro.web.rest;

import io.dentall.totoro.business.service.report.FollowupReportService;
import io.dentall.totoro.business.service.report.TreatmentReportService;
import io.dentall.totoro.business.service.report.context.ReportRunningCheck;
import io.dentall.totoro.business.service.report.followup.FollowupBookSetting;
import io.dentall.totoro.business.service.report.treatment.TreatmentBookSetting;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.FollowupReportVM;
import io.dentall.totoro.web.rest.vm.TreatmentReportVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

import static io.dentall.totoro.business.service.report.context.ReportCategory.FOLLOWUP;
import static io.dentall.totoro.business.service.report.context.ReportCategory.TREATMENT;

@RestController
@RequestMapping("/api/report")
public class ReportResource {

    private static final Logger log = LoggerFactory.getLogger(ReportResource.class);

    private final TreatmentReportService treatmentReportService;

    private final FollowupReportService followupReportService;

    private final Executor taskExecutor;

    public ReportResource(TreatmentReportService treatmentReportService, FollowupReportService followupReportService, @Qualifier("taskExecutor") Executor taskExecutor) {
        this.treatmentReportService = treatmentReportService;
        this.followupReportService = followupReportService;
        this.taskExecutor = taskExecutor;
    }

    @ReportRunningCheck(category = TREATMENT, concurrentUpLimit = 1)
    @PostMapping("/treatment")
    public ResponseEntity<String> createTreatmentReport(
        @RequestBody TreatmentBookSetting bookSetting) {
        log.debug("REST request to createTreatmentReport : {}", bookSetting);

        taskExecutor.execute(() -> {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(512000)) {
                treatmentReportService.createReport(bookSetting, outputStream);
            } catch (Exception e) {
                log.error("output treatment report", e);
            }
        });

        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/treatment")
    public ResponseEntity<List<TreatmentReportVM>> getTreatmentReport() {
        log.debug("REST request to getTreatmentReport");
        return ResponseEntity.ok(treatmentReportService.getReport());
    }

    @PatchMapping("/treatment/{id}/cancel")
    public ResponseEntity<TreatmentReportVM> cancelTreatmentReport(@PathVariable("id") long id) {
        log.debug("REST request to cancelTreatmentReport, id: {}", id);
        Optional<TreatmentReportVM> optional = treatmentReportService.cancelReport(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new BadRequestAlertException("Report not found", "TreatmentReport", "notfound");
        }
    }

    @ReportRunningCheck(category = FOLLOWUP, concurrentUpLimit = 1)
    @PostMapping("/followup")
    public ResponseEntity<String> createFollowupReport(
        @RequestBody FollowupBookSetting bookSetting) {
        log.debug("REST request to createFollowupReport : {}", bookSetting);

        taskExecutor.execute(() -> {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(512000)) {
                followupReportService.createReport(bookSetting, outputStream);
            } catch (Exception e) {
                log.error("output followup report", e);
            }
        });

        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/followup")
    public ResponseEntity<List<FollowupReportVM>> getFollowupReport() {
        log.debug("REST request to getFollowupReport");
        return ResponseEntity.ok(followupReportService.getReport());
    }

    @PatchMapping("/followup/{id}/cancel")
    public ResponseEntity<FollowupReportVM> cancelFollowupReport(@PathVariable("id") long id) {
        log.debug("REST request to cancelFollowupReport, id: {}", id);
        Optional<FollowupReportVM> optional = followupReportService.cancelReport(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new BadRequestAlertException("Report not found", "FollowupReport", "notfound");
        }
    }

}

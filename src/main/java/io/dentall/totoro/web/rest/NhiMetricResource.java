package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.nhi.metric.*;
import io.dentall.totoro.business.service.nhi.metric.dto.CompositeDistrictDto;
import io.dentall.totoro.business.service.nhi.metric.vm.MetricLVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportBodyVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportQueryStringVM;
import io.dentall.totoro.domain.NhiMetricReport;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.domain.enumeration.BackupFileCatalog;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import io.dentall.totoro.repository.NhiMetricReportRepository;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.mapper.NhiMetricReportMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api/nhi/metric")
public class NhiMetricResource {

    private final Logger log = LoggerFactory.getLogger(NhiMetricResource.class);

    private final MetricService metricService;

    private final NhiMetricReportService nhiMetricReportService;

    private final UserService userService;

    private final NhiMetricReportRepository nhiMetricReportRepository;

    private final ApplicationContext applicationContext;

    private Integer MAX_LOCK;

    public NhiMetricResource(
        MetricService metricService,
        NhiMetricReportService nhiMetricReportService,
        UserService userService,
        NhiMetricReportRepository nhiMetricReportRepository,
        ApplicationContext applicationContext,
        @Value("${nhi.metric.maxLock}") Integer maxLock
    ) {
        this.metricService = metricService;
        this.nhiMetricReportService = nhiMetricReportService;
        this.userService = userService;
        this.nhiMetricReportRepository = nhiMetricReportRepository;
        this.applicationContext = applicationContext;
        this.MAX_LOCK = maxLock;
    }

    @GetMapping("/dashboard")
    @Timed
    public ResponseEntity<Map<String, List<MetricLVM>>> getDashboardMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalIds) {
        log.debug("REST request to dashboard : begin={}, excludeDisposalId={}", begin, excludeDisposalIds);

        List<MetricLVM> vm = metricService.getDashboardMetric(begin, excludeDisposalIds);
        Map<String, List<MetricLVM>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/composite-metric")
    @Timed
    public ResponseEntity<CompositeDistrictDto> getCompositeMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalIds) {
        log.debug("REST request to getCompositeMetric : begin={}, excludeDisposalId={}", begin, excludeDisposalIds);

        Optional<User> currentUser = userService.getUserWithAuthorities();
        CompositeDistrictDto vm = metricService.getCompositeDistrictMetric(begin, excludeDisposalIds, new ArrayList<>(), asList(SouthDistrictService.class), currentUser.get());

        return ResponseEntity.ok().body(vm);
    }

    @PostMapping("/report")
    @Timed
    @Transactional
    public ResponseEntity<String> generateNhiMetricReport(@RequestBody NhiMetricReportBodyVM nhiMetricReportBodyVM) {
        log.debug("REST request to composite district : begin={}, excludeDisposalId={}, doctorIds={}",
            nhiMetricReportBodyVM.getBegin(),
            nhiMetricReportBodyVM.getExcludeDisposalId(),
            nhiMetricReportBodyVM.getDoctorIds()
        );

        Set<Class<? extends DistrictService>> reportTypeServiceList = new HashSet<>();

        Optional<User> currentUser = userService.getUserWithAuthorities();

        if (!currentUser.isPresent()) {
            return ResponseEntity.badRequest()
                .body("Can not found current login user.");
        }

        if (
            nhiMetricReportRepository.countByStatusOrderByCreatedDateDesc(BatchStatus.LOCK) >= MAX_LOCK
        ) {
            return ResponseEntity.badRequest()
                .body(
                    String.format(
                        "Excess maximum lock (%s), wait until procedure done and download result from url",
                        this.MAX_LOCK
                    )
                );
        }

        NhiMetricReport reportRecord = nhiMetricReportRepository.save(
            NhiMetricReportMapper.INSTANCE.convertBodyToDomain(
                nhiMetricReportBodyVM
            )
        );

        final Long finalRecordId = reportRecord.getId();
        ForkJoinPool.commonPool().submit(() -> {
            nhiMetricReportBodyVM.getNhiMetricReportTypes()
                .forEach(t -> {
                    switch (t) {
                        case TAIPEI_DISTRICT:
                            reportTypeServiceList.add(TaipeiDistrictService.class);
                            break;
                        case NORTH_DISTRICT:
                            reportTypeServiceList.add(NorthDistrictService.class);
                            break;
                        case MIDDLE_DISTRICT:
                            reportTypeServiceList.add(MiddleDistrictService.class);
                            break;
                        case SOUTH_DISTRICT:
                            reportTypeServiceList.add(SouthDistrictService.class);
                            break;
                        case KAO_PING_REDUCTION_DISTRICT:
                            reportTypeServiceList.add(KaoPingDistrictReductionService.class);
                            break;
                        case KAO_PING_REGULAR_DISTRICT:
                            reportTypeServiceList.add(KaoPingDistrictRegularService.class);
                            break;
                        case EAST_DISTRICT:
                            reportTypeServiceList.add(EastDistrictService.class);
                            break;
                    }
                });

            CompositeDistrictDto dto = metricService.getCompositeDistrictMetric(
                nhiMetricReportBodyVM.getBegin(),
                nhiMetricReportBodyVM.getExcludeDisposalId(),
                nhiMetricReportBodyVM.getDoctorIds(),
                new ArrayList<>(reportTypeServiceList),
                currentUser.get()
            );

            NhiMetricReport r = nhiMetricReportRepository.findById(finalRecordId)
                .orElse(null);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // FileOutputStream outputStream = new FileOutputStream("test.xls"); // develop version of output for store wb as local file
            try {
                nhiMetricReportService.generateNhiMetricReport(nhiMetricReportBodyVM, dto, outputStream);

                ImageGcsBusinessService imageGcsBusinessService = applicationContext.getBean(ImageGcsBusinessService.class);
                String gcsPath = imageGcsBusinessService.getClinicName()
                    .concat("/")
                    .concat(BackupFileCatalog.NHI_METRIC_REPORT.getRemotePath())
                    .concat("/");
                String fileName = String.format(
                        "%s_NhiPoints_%s(報告產生時間%s)",
                        DateTimeUtil.transformLocalDateToFormatedStringYyyymm(
                            nhiMetricReportBodyVM.getBegin()
                        ),
                        imageGcsBusinessService.getClinicName(),
                        DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                            .withZone(ZoneId.of("Asia/Taipei"))
                            .format(Instant.now())
                    )
                    .concat(".xls");
                String fileUrl = imageGcsBusinessService.getUrlForDownload()
                    .concat(imageGcsBusinessService.getClinicName())
                    .concat("/")
                    .concat(BackupFileCatalog.NHI_METRIC_REPORT.getRemotePath())
                    .concat("/")
                    .concat(fileName);
                imageGcsBusinessService.uploadFile(
                    gcsPath,
                    fileName,
                    outputStream.toByteArray(),
                    BackupFileCatalog.NHI_METRIC_REPORT.getFileExtension()
                );

                if (r != null) {
                    r.setStatus(BatchStatus.DONE);
                    r.getComment().setUrl(fileUrl);
                    nhiMetricReportRepository.save(r);
                    nhiMetricReportRepository.flush();
                }

                outputStream.close();
            } catch (Exception e) {
                if (r != null) {
                    r.setStatus(BatchStatus.FAILURE);
                    r.getComment().setErrorMessage(e.getMessage());
                    nhiMetricReportRepository.save(r);
                    nhiMetricReportRepository.flush();
                }
            }
        });

        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/report")
    @Timed
    public ResponseEntity<List<NhiMetricReport>> getNhiMetricReports(NhiMetricReportQueryStringVM queryStringVM) {
        List<NhiMetricReport> result = nhiMetricReportRepository.findByYearMonthAndCreatedByOrderByCreatedDateDesc(
            DateTimeUtil.transformIntYyyymmToFormatedStringYyyymm(
                queryStringVM.getYyyymm()
            ),
            queryStringVM.getCreatedBy()
        );

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/report/{id}/delock")
    @Timed
    @Transactional
    public ResponseEntity<NhiMetricReport> delockNhiMetricReport(
        @PathVariable Long id,
        @RequestBody(required = false) String cancelReason
    ) {
        NhiMetricReport report = nhiMetricReportRepository.findById(id)
                .orElseThrow(() -> new BadRequestAlertException("Report not found", "NhiMetricReport", "notfound"));
        report.setStatus(BatchStatus.CANCEL);
        report.getComment().setCancelReason(cancelReason);

        return ResponseEntity.ok(report);
    }
}

package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.metric.*;
import io.dentall.totoro.business.service.nhi.metric.dto.*;
import io.dentall.totoro.business.service.nhi.metric.vm.MetricLVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportBodyVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/nhi/metric")
public class NhiMetricResource {

    private final Logger log = LoggerFactory.getLogger(NhiMetricResource.class);

    private final MetricService metricService;

    private final NhiMetricReportService nhiMetricReportService;

    public NhiMetricResource(
        MetricService metricService,
        NhiMetricReportService nhiMetricReportService
    ) {
        this.metricService = metricService;
        this.nhiMetricReportService = nhiMetricReportService;
    }

    @GetMapping("/dashboard")
    @Timed
    public ResponseEntity<Map<String, List<MetricLVM>>> getDashboardMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId) {
        log.debug("REST request to dashboard : begin={}, excludeDisposalId={}", begin, excludeDisposalId);

        List<MetricLVM> vm = metricService.getDashboardMetric(begin, excludeDisposalId);
        Map<String, List<MetricLVM>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }


    @GetMapping("/taipei-district")
    @Timed
    public ResponseEntity<Map<String, List<TaipeiDistrictDto>>> getTaipeiDistrictMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to taipei district : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        List<TaipeiDistrictDto> vm = metricService.getTaipeiDistrictMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<TaipeiDistrictDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/north-district")
    @Timed
    public ResponseEntity<Map<String, List<NorthDistrictDto>>> getNorthDistrictMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to north district : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        List<NorthDistrictDto> vm = metricService.getNorthDistrictMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<NorthDistrictDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/middle-district")
    @Timed
    public ResponseEntity<Map<String, List<MiddleDistrictDto>>> getMiddleDistrictMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to middle district : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        List<MiddleDistrictDto> vm = metricService.getMiddleDistrictMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<MiddleDistrictDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/south-district")
    @Timed
    public ResponseEntity<Map<String, List<SouthDistrictDto>>> getSouthDistrictMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to south district : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        List<SouthDistrictDto> vm = metricService.getSouthDistrictMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<SouthDistrictDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/east-district")
    @Timed
    public ResponseEntity<Map<String, List<EastDistrictDto>>> getEastDistrictMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to east district : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        List<EastDistrictDto> vm = metricService.getEastDistrictMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<EastDistrictDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/kao-ping-district-regular")
    @Timed
    public ResponseEntity<Map<String, List<KaoPingDistrictRegularDto>>> getKaoPingDistrictRegularMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to kao-ping district regular : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        List<KaoPingDistrictRegularDto> vm = metricService.getKaoPingDistrictRegularMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<KaoPingDistrictRegularDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/kao-ping-district-reduction")
    @Timed
    public ResponseEntity<Map<String, List<KaoPingDistrictReductionDto>>> getKaoPingDistrictReductionMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to kao-ping district reduction : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        List<KaoPingDistrictReductionDto> vm = metricService.getKaoPingDistrictReductionMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<KaoPingDistrictReductionDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/composite-district")
    @Timed
    public ResponseEntity<CompositeDistrictDto> getCompositeDistrictReductionMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {
        log.debug("REST request to composite district : begin={}, excludeDisposalId={}, doctorIds={}", begin, excludeDisposalId, doctorIds);

        CompositeDistrictDto dto = metricService.getCompositeDistrictMetric(begin, excludeDisposalId, doctorIds,
            Arrays.asList(TaipeiDistrictService.class,
                NorthDistrictService.class,
                MiddleDistrictService.class,
                SouthDistrictService.class,
                EastDistrictService.class,
                KaoPingDistrictRegularService.class,
                KaoPingDistrictReductionService.class));

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/report")
    @Timed
    public ResponseEntity<String> generateNhiMetricReport(@RequestBody NhiMetricReportBodyVM nhiMetricReportBodyVM) throws IOException {
        log.debug("REST request to composite district : begin={}, excludeDisposalId={}, doctorIds={}",
            nhiMetricReportBodyVM.getBegin(),
            nhiMetricReportBodyVM.getExcludeDisposalId(),
            nhiMetricReportBodyVM.getDoctorIds()
        );

        List<Class<? extends DistrictService>> reportTypeServiceList = new ArrayList<>();
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
                        reportTypeServiceList.add(EastDistrictService.class);
                        break;
                    case KAO_PING_REGULAR_DISTRICT:
                        reportTypeServiceList.add(KaoPingDistrictRegularService.class);
                        break;
                    case EAST_DISTRICT:
                        reportTypeServiceList.add(KaoPingDistrictReductionService.class);
                        break;
                }
            });

        CompositeDistrictDto dto = metricService.getCompositeDistrictMetric(
            nhiMetricReportBodyVM.getBegin(),
            nhiMetricReportBodyVM.getExcludeDisposalId(),
            nhiMetricReportBodyVM.getDoctorIds(),
            reportTypeServiceList
        );

        nhiMetricReportService.generateNhiMetricReport(nhiMetricReportBodyVM, dto);

        return ResponseEntity.ok().body("ok");
    }
}

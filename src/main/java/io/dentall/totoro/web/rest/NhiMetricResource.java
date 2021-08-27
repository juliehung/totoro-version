package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.metric.MetricService;
import io.dentall.totoro.business.service.nhi.metric.vm.MetricLVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportBodyVM;
import io.dentall.totoro.business.vm.nhi.NhiMetricReportQueryStringVM;
import io.dentall.totoro.domain.NhiMetricReport;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import io.dentall.totoro.repository.NhiMetricReportRepository;
import io.dentall.totoro.service.mapper.NhiMetricReportMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nhi/metric")
public class NhiMetricResource {

    private final Logger log = LoggerFactory.getLogger(NhiMetricResource.class);

    private final MetricService metricService;

    public NhiMetricResource(
        MetricService metricService
    ) {
        this.metricService = metricService;
    }

    @GetMapping("/dashboard")
    @Timed
    public ResponseEntity<Map<String, List<MetricLVM>>> getMetricL(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId) {

        List<MetricLVM> vm = metricService.getDashboardMetric(begin, excludeDisposalId);
        Map<String, List<MetricLVM>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @Profile("img-gcs")
    @PostMapping("/reports")
    @Timed
    @Transactional
    public ResponseEntity<String> generateNhiMetricReport(@RequestBody NhiMetricReportBodyVM nhiMetricReportBodyVM) {
        return ResponseEntity.ok(metricService.generateNhiMetricReport(nhiMetricReportBodyVM));
    }

    @Profile("img-gcs")
    @GetMapping("/reports")
    @Timed
    @Transactional
    public ResponseEntity<List<NhiMetricReport>> getNhiMetricReports(NhiMetricReportQueryStringVM queryStringVM) {
        return ResponseEntity.ok().body(metricService.getNhiMetricReports(queryStringVM));
    }
}

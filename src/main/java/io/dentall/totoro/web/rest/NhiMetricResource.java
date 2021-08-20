package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.metric.MetricDashboardService;
import io.dentall.totoro.business.service.nhi.metric.vm.MetricLVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/nhi/metric")
public class NhiMetricResource {
    private final Logger log = LoggerFactory.getLogger(NhiMetricResource.class);

    private final MetricDashboardService metricDashboardService;

    public NhiMetricResource(MetricDashboardService metricDashboardService) {
        this.metricDashboardService = metricDashboardService;
    }

    @GetMapping("/L")
    @Timed
    public ResponseEntity<List<MetricLVM>> getMetricL(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId) {

        List<MetricLVM> vm = metricDashboardService.metric(begin, excludeDisposalId);

        return ResponseEntity.ok().body(vm);
    }
}

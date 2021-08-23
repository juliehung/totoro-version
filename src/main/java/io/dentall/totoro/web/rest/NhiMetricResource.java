package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.metric.MetricService;
import io.dentall.totoro.business.service.nhi.metric.dto.GiantMetricDto;
import io.dentall.totoro.business.service.nhi.metric.vm.MetricLVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nhi/metric")
public class NhiMetricResource {

    private final Logger log = LoggerFactory.getLogger(NhiMetricResource.class);

    private final MetricService metricService;

    public NhiMetricResource(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping("/dashboard")
    @Timed
    public ResponseEntity<Map<String, List<MetricLVM>>> getDashboardMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId) {

        List<MetricLVM> vm = metricService.getDashboardMetric(begin, excludeDisposalId);
        Map<String, List<MetricLVM>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }


    @GetMapping("/taipei-district")
    @Timed
    public ResponseEntity<Map<String, List<GiantMetricDto>>> getTaipeiDistrictMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {

        List<GiantMetricDto> vm = metricService.getTaipeiDistrictMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<GiantMetricDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/middle-district")
    @Timed
    public ResponseEntity<Map<String, List<GiantMetricDto>>> getMiddleDistrictMetric(
        @RequestParam LocalDate begin,
        @RequestParam(required = false) List<Long> excludeDisposalId,
        @RequestParam(required = false) List<Long> doctorIds) {

        List<GiantMetricDto> vm = metricService.getMiddleDistrictMetric(begin, excludeDisposalId, doctorIds);
        Map<String, List<GiantMetricDto>> map = new HashMap<>();
        map.put("metrics", vm);

        return ResponseEntity.ok().body(map);
    }
}

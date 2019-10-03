package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiAbnormalityService;
import io.dentall.totoro.business.service.nhi.NhiStatisticService;
import io.dentall.totoro.business.vm.nhi.NhiAbnormality;
import io.dentall.totoro.business.vm.nhi.NhiStatisticDashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

/**
 *
 * REST controller for managing nhi statistics.
 */
@RestController
@RequestMapping("/api/business/nhi/statistics")
public class NhiStatisticBusinessResource {
    private final Logger log = LoggerFactory.getLogger(NhiStatisticBusinessResource.class);

    private final NhiAbnormalityService nhiAbnormalityService;

    private final NhiStatisticService nhiStatisticService;

    public NhiStatisticBusinessResource(
        NhiAbnormalityService nhiAbnormalityService,
        NhiStatisticService nhiStatisticService
    ) {
        this.nhiAbnormalityService = nhiAbnormalityService;
        this.nhiStatisticService = nhiStatisticService;
    }

    @GetMapping("/abnormality")
    @Timed
    public ResponseEntity<NhiAbnormality> getNhiAbnormalityDoctors(@RequestParam int yyyymm) {
        log.debug("REST request to get NhiAbnormality by year month: {}", yyyymm);
        return new ResponseEntity<>(nhiAbnormalityService.getNhiAbnormality(yyyymm), HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    @Timed
    public ResponseEntity<List<NhiStatisticDashboard>> getDashboard(@RequestParam int yyyymm) {
        log.debug("REST request to get Dashboard by year month: {}", yyyymm);
        return new ResponseEntity<>(nhiStatisticService.calculate(YearMonth.of(yyyymm / 100, yyyymm % 100)), HttpStatus.OK);
    }
}
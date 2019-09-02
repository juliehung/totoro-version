package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.service.nhi.NhiAbnormalityService;
import io.dentall.totoro.business.vm.nhi.NhiAbnormality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * REST controller for managing nhi statistics.
 */
@RestController
@RequestMapping("/api/business/nhi/statistics")
public class NhiStatisticBusinessResource {
    private final Logger log = LoggerFactory.getLogger(NhiStatisticBusinessResource.class);

    private final NhiAbnormalityService nhiAbnormalityService;

    public NhiStatisticBusinessResource(NhiAbnormalityService nhiAbnormalityService) {
        this.nhiAbnormalityService = nhiAbnormalityService;
    }

    @GetMapping("/abnormality")
    @Timed
    public ResponseEntity<NhiAbnormality> getNhiAbnormalityDoctors(@RequestParam int yyyymm) {
        log.debug("REST request to get NhiAbnormality by year month: {}", yyyymm);
        return new ResponseEntity<>(nhiAbnormalityService.getNhiAbnormality(yyyymm), HttpStatus.OK);
    }
}
